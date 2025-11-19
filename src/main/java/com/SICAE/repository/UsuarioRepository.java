package com.sicae.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
