package com.sicae.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.PuntoAcceso;

public interface PuntoAccesoRepository extends MongoRepository<PuntoAcceso, String> {
}
