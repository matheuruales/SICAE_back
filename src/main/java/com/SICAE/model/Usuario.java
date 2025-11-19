package com.sicae.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "usuarios")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private String id;

    private String nombreCompleto;

    @Indexed(unique = true)
    private String correo;

    private String hashPassword;

    private Rol rol;

    @Indexed(unique = true, sparse = true)
    private String personaId;

    private boolean activo;

    private Instant fechaCreacion;

    private Instant ultimoAcceso;
}
