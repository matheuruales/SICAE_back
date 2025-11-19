package com.sicae.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "credenciales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credencial {
    @Id
    private String id;

    @Indexed
    private String personaId;

    private EstadoCredencial estado;
    private Instant emitidaEn;
    private Instant expiraEn;
    private TipoCredencial tipo;
    private QRToken qrToken;
    private String motivoRevocacion;
}
