package com.sicae.dto;

import java.time.LocalDate;

import com.sicae.model.TipoPersona;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonaRequest(
        @NotBlank String nombreCompleto,
        @NotBlank String documento,
        LocalDate fechaNacimiento,
        String telefono,
        @NotNull TipoPersona tipo,
        String numeroEmpleado,
        String departamento,
        LocalDate fechaIngreso,
        String empresa,
        String personaContacto,
        String motivoVisita,
        String empresaContratista,
        String numeroContacto,
        LocalDate fechaVencimientoContrato) {
}
