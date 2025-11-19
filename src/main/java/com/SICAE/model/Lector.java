package com.sicae.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "lectores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lector {
    @Id
    private String id;
    private String apiKey;
    private boolean activo;
    private String modelo;
    private String versionFirmware;
}
