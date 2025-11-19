package com.sicae.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.Credencial;

public interface CredencialRepository extends MongoRepository<Credencial, String> {
    Optional<Credencial> findByQrTokenCode(String code);
}
