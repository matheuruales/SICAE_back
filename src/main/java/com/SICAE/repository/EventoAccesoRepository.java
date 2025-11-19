package com.sicae.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sicae.model.EventoAcceso;

public interface EventoAccesoRepository extends MongoRepository<EventoAcceso, String> {
    EventoAcceso findTopByPersonaIdAndPuntoAccesoIdOrderByFechaHoraDesc(String personaId, String puntoAccesoId);
    EventoAcceso findTopByPersonaIdOrderByFechaHoraDesc(String personaId);
}
