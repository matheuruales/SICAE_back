package com.sicae.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidarQrRequest(
        @NotBlank String qrCode,
        String puntoAccesoId,
        String ipLector) {
}
