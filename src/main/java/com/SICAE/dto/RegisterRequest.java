package com.sicae.dto;

import com.sicae.model.TipoPersona;
import com.sicae.model.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank String nombreCompleto,
        @Email @NotBlank String correo,
        @NotBlank @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
        @NotNull Rol rol,
        @NotBlank String documento,
        @NotNull TipoPersona tipoPersona,
        String telefono,
        String empresa,
        String personaContacto,
        String motivoVisita) {
}
