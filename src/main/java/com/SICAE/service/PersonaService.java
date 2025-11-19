package com.sicae.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sicae.dto.PersonaRequest;
import com.sicae.model.Persona;
import com.sicae.repository.PersonaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    public Persona registrar(PersonaRequest request) {
        Persona persona = Persona.builder()
                .nombreCompleto(request.nombreCompleto())
                .documento(request.documento())
                .fechaNacimiento(request.fechaNacimiento())
                .telefono(request.telefono())
                .tipo(request.tipo())
                .numeroEmpleado(request.numeroEmpleado())
                .departamento(request.departamento())
                .fechaIngreso(request.fechaIngreso())
                .empresa(request.empresa())
                .personaContacto(request.personaContacto())
                .motivoVisita(request.motivoVisita())
                .empresaContratista(request.empresaContratista())
                .numeroContacto(request.numeroContacto())
                .fechaVencimientoContrato(request.fechaVencimientoContrato())
                .build();
        return personaRepository.save(persona);
    }

    public List<Persona> listar() {
        return personaRepository.findAll();
    }
}
