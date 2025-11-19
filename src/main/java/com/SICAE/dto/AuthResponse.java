package com.sicae.dto;

import com.sicae.model.Rol;

public record AuthResponse(String token, String usuarioId, String nombreCompleto, String correo, Rol rol, String personaId) {
}
