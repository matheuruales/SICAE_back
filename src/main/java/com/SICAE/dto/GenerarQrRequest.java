package com.sicae.dto;

import jakarta.validation.constraints.NotBlank;

public record GenerarQrRequest(@NotBlank String personaId, Integer vigenciaSegundos) {
}
