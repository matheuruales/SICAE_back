package com.sicae.dto;

import com.sicae.model.Rol;

public record UsuarioResponse(String usuarioId, String nombreCompleto, String correo, Rol rol, String personaId) {
}
