package com.sicae.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.Lector;

public interface LectorRepository extends MongoRepository<Lector, String> {
    Optional<Lector> findByApiKey(String apiKey);
}
