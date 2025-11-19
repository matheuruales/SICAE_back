package com.sicae.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "eventos_acceso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoAcceso {
    @Id
    private String id;
    private String personaId;
    private String credencialId;
    private String puntoAccesoId;
    private Instant fechaHora;
    private ResultadoAcceso resultado;
    private String motivo;
    private String qrCode;
    private String ipLector;
    private TipoEvento tipoEvento;
    private Instant fechaHoraSalida;
}
