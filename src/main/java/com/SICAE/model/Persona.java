package com.sicae.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "personas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {
    @Id
    private String id;

    @Indexed(unique = true)
    private String usuarioId;

    private String nombreCompleto;
    private String documento;
    private LocalDate fechaNacimiento;
    private String telefono;
    private TipoPersona tipo;

    // Atributos específicos por tipo
    private String numeroEmpleado;
    private String departamento;
    private LocalDate fechaIngreso;

    private String empresa;
    private String personaContacto;
    private String motivoVisita;

    private String empresaContratista;
    private String numeroContacto;
    private LocalDate fechaVencimientoContrato;
}
