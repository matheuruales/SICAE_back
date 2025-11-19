package com.sicae.dto;

import com.sicae.model.TipoPunto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PuntoAccesoRequest(
        @NotBlank String nombre,
        @NotBlank String ubicacion,
        @NotNull TipoPunto tipo,
        boolean activo) {
}
