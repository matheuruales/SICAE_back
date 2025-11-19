package com.sicae.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QRToken {
    private String code;
    private boolean usado;
    private Instant fechaGeneracion;
    private Instant expiraEn;
}
