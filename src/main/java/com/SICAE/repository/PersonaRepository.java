package com.sicae.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.Persona;

public interface PersonaRepository extends MongoRepository<Persona, String> {
    java.util.Optional<Persona> findByUsuarioId(String usuarioId);
}
