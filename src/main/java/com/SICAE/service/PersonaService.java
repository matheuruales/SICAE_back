package com.sicae.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sicae.dto.PersonaRequest;
import com.sicae.model.Persona;
import com.sicae.repository.PersonaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    public Persona registrar(PersonaRequest request) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Las personas se crean automáticamente junto a su usuario. Registra un usuario nuevo.");
    }

    public List<Persona> listar() {
        return personaRepository.findAll();
    }
}
