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
import com.sicae.model.Usuario;
import com.sicae.repository.UsuarioRepository;
import com.sicae.security.JwtService;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
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
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token, usuario.getId(), usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getRol());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.correo().toLowerCase(), request.password()));

        Usuario usuario = usuarioRepository.findByCorreo(request.correo().toLowerCase())
                .orElseThrow(() -> new ValidationException("Credenciales inválidas"));
        usuario.setUltimoAcceso(Instant.now());
        usuarioRepository.save(usuario);
        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token, usuario.getId(), usuario.getNombreCompleto(), usuario.getCorreo(), usuario.getRol());
    }

    public java.util.List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponse(u.getId(), u.getNombreCompleto(), u.getCorreo(), u.getRol()))
                .toList();
    }
}
