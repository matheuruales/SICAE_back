package com.sicae.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "puntos_acceso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoAcceso {
    @Id
    private String id;
    private String nombre;
    private String ubicacion;
    private TipoPunto tipo;
    private boolean activo;
    private Instant fechaInstalacion;
}
