package com.sicae.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sicae.dto.GenerarQrRequest;
import com.sicae.dto.ValidarQrRequest;
import com.sicae.model.Credencial;
import com.sicae.model.EstadoCredencial;
import com.sicae.model.EventoAcceso;
import com.sicae.model.Persona;
import com.sicae.model.QRToken;
import com.sicae.model.ResultadoAcceso;
import com.sicae.model.TipoCredencial;
import com.sicae.model.TipoEvento;
import com.sicae.repository.CredencialRepository;
import com.sicae.repository.EventoAccesoRepository;
import com.sicae.repository.PersonaRepository;
import com.sicae.repository.UsuarioRepository;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredencialService {

    private final CredencialRepository credencialRepository;
    private final PersonaRepository personaRepository;
    private final EventoAccesoRepository eventoAccesoRepository;
    private final UsuarioRepository usuarioRepository;

    public Credencial generarQr(GenerarQrRequest request) {
        String personaId = request.personaId();
        if (personaId == null || personaId.isBlank()) {
            personaId = obtenerPersonaDelUsuarioActual();
        }

        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new ValidationException("Persona no encontrada"));
        Instant now = Instant.now();
        Instant expiracion = now.plusSeconds(request.vigenciaSegundos() != null ? request.vigenciaSegundos() : 60);

        QRToken token = QRToken.builder()
                .code(UUID.randomUUID().toString())
                .usado(false)
                .fechaGeneracion(now)
                .expiraEn(expiracion)
                .build();

        Credencial credencial = Credencial.builder()
                .personaId(persona.getId())
                .estado(EstadoCredencial.ACTIVA)
                .emitidaEn(now)
                .expiraEn(expiracion)
                .tipo(TipoCredencial.QR)
                .qrToken(token)
                .build();

        return credencialRepository.save(credencial);
    }

    public List<Credencial> listar() {
        return credencialRepository.findAll();
    }

    public EventoAcceso validarQr(ValidarQrRequest request) {
        Instant now = Instant.now();
        ResultadoAcceso resultado = ResultadoAcceso.DENEGADO;
        String motivo = "Código no encontrado";
        Credencial credencial = credencialRepository.findByQrTokenCode(request.qrCode()).orElse(null);
        TipoEvento tipoEvento = TipoEvento.INTENTO;

        if (credencial != null) {
            if (credencial.getEstado() == EstadoCredencial.REVOCADA || credencial.getEstado() == EstadoCredencial.INACTIVA) {
                motivo = "Credencial revocada o inactiva";
            } else if (credencial.getQrToken() == null) {
                motivo = "Token no disponible";
            } else if (Boolean.TRUE.equals(credencial.getQrToken().isUsado())) {
                motivo = "Ya fue usado";
            } else if (credencial.getQrToken().getExpiraEn().isBefore(now)) {
                motivo = "Token expirado";
                credencial.setEstado(EstadoCredencial.EXPIRADA);
                credencialRepository.save(credencial);
            } else {
                resultado = ResultadoAcceso.PERMITIDO;
                motivo = "Acceso permitido";
                tipoEvento = resolverTipoEntradaSalida(credencial.getPersonaId(), request.puntoAccesoId());
                credencial.getQrToken().setUsado(true);
                credencialRepository.save(credencial);
            }
        }

        EventoAcceso evento = EventoAcceso.builder()
                .credencialId(credencial != null ? credencial.getId() : null)
                .personaId(credencial != null ? credencial.getPersonaId() : null)
                .fechaHora(now)
                .resultado(resultado)
                .motivo(motivo)
                .qrCode(request.qrCode())
                .ipLector(request.ipLector())
                .puntoAccesoId(request.puntoAccesoId())
                .tipoEvento(resultado == ResultadoAcceso.PERMITIDO ? tipoEvento : TipoEvento.INTENTO)
                .build();

        return eventoAccesoRepository.save(evento);
    }

    public Credencial revocar(String credencialId, String motivo) {
        Credencial credencial = credencialRepository.findById(credencialId)
                .orElseThrow(() -> new ValidationException("Credencial no encontrada"));
        credencial.setEstado(EstadoCredencial.REVOCADA);
        credencial.setMotivoRevocacion(motivo);
        return credencialRepository.save(credencial);
    }

    private TipoEvento resolverTipoEntradaSalida(String personaId, String puntoAccesoId) {
        if (personaId == null) {
            return TipoEvento.ENTRADA;
        }

        EventoAcceso ultimo = null;
        if (puntoAccesoId != null) {
            ultimo = eventoAccesoRepository.findTopByPersonaIdAndPuntoAccesoIdOrderByFechaHoraDesc(personaId, puntoAccesoId);
        }
        if (ultimo == null) {
            ultimo = eventoAccesoRepository.findTopByPersonaIdOrderByFechaHoraDesc(personaId);
        }

        if (ultimo != null && ultimo.getResultado() == ResultadoAcceso.PERMITIDO && ultimo.getTipoEvento() == TipoEvento.ENTRADA) {
            return TipoEvento.SALIDA;
        }
        return TipoEvento.ENTRADA;
    }

    private String obtenerPersonaDelUsuarioActual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ValidationException("No se pudo resolver el usuario autenticado para generar la credencial");
        }

        String correo = authentication.getName();
        com.sicae.model.Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ValidationException("Usuario no encontrado para generar la credencial"));

        if (usuario.getPersonaId() == null || usuario.getPersonaId().isBlank()) {
            throw new ValidationException("El usuario no tiene una persona vinculada");
        }

        return usuario.getPersonaId();
    }
}
