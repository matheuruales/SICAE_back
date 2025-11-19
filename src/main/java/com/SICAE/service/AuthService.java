package com.sicae.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sicae.dto.AuthResponse;
import com.sicae.dto.LoginRequest;
import com.sicae.dto.RegisterRequest;
import com.sicae.dto.UsuarioResponse;
import com.sicae.model.Persona;
import com.sicae.model.Rol;
import com.sicae.model.TipoPersona;
import com.sicae.model.Usuario;
import com.sicae.repository.PersonaRepository;
import com.sicae.repository.UsuarioRepository;
import com.sicae.security.JwtService;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.correo())) {
            throw new ValidationException("El correo ya está registrado");
        }

        Usuario usuario = Usuario.builder()
                .nombreCompleto(request.nombreCompleto())
                .correo(request.correo().toLowerCase())
                .hashPassword(passwordEncoder.encode(request.password()))
                .rol(request.rol())
                .activo(true)
                .fechaCreacion(Instant.now())
                .build();

        usuarioRepository.save(usuario);

        Persona persona = Persona.builder()
                .usuarioId(usuario.getId())
                .nombreCompleto(request.nombreCompleto())
                .documento(request.documento())
                .telefono(request.telefono())
                .tipo(request.tipoPersona())
                .empresa(request.empresa())
                .personaContacto(request.personaContacto())
                .motivoVisita(request.motivoVisita())
                .build();

        personaRepository.save(persona);
        usuario.setPersonaId(persona.getId());
        String token = jwtService.generateToken(usuario);
        usuarioRepository.save(usuario);

        return new AuthResponse(token, usuario.getId(), usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getRol(),
                usuario.getPersonaId());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.correo().toLowerCase(), request.password()));

        Usuario usuario = usuarioRepository.findByCorreo(request.correo().toLowerCase())
                .orElseThrow(() -> new ValidationException("Credenciales inválidas"));
        Persona persona = ensurePersonaCreada(usuario);
        usuario.setUltimoAcceso(Instant.now());
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token, usuario.getId(), usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getRol(),
                persona != null ? persona.getId() : usuario.getPersonaId());
    }

    public java.util.List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNombreCompleto(), u.getCorreo(), u.getRol(), u.getPersonaId()))
                .toList();
    }

    private Persona ensurePersonaCreada(Usuario usuario) {
        if (usuario.getPersonaId() != null) {
            return personaRepository.findById(usuario.getPersonaId()).orElseGet(() -> crearPersonaParaUsuario(usuario));
        }
        return crearPersonaParaUsuario(usuario);
    }

    private Persona crearPersonaParaUsuario(Usuario usuario) {
        Persona reutilizada = personaRepository.findByUsuarioId(usuario.getId()).orElse(null);
        if (reutilizada != null) {
            usuario.setPersonaId(reutilizada.getId());
            usuarioRepository.save(usuario);
            return reutilizada;
        }

        Persona creada = Persona.builder()
                .usuarioId(usuario.getId())
                .nombreCompleto(usuario.getNombreCompleto())
                .documento(usuario.getCorreo())
                .tipo(tipoPersonaPorRol(usuario.getRol()))
                .build();
        personaRepository.save(creada);
        usuario.setPersonaId(creada.getId());
        usuarioRepository.save(usuario);
        return creada;
    }

    private TipoPersona tipoPersonaPorRol(Rol rol) {
        if (rol == Rol.ADMIN || rol == Rol.SEGURIDAD) {
            return TipoPersona.EMPLEADO;
        }
        return TipoPersona.VISITANTE;
    }
}
