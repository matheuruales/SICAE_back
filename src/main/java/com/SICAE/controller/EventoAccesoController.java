package com.sicae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicae.model.EventoAcceso;
import com.sicae.repository.EventoAccesoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoAccesoController {

    private final EventoAccesoRepository eventoAccesoRepository;

    @GetMapping
    public ResponseEntity<List<EventoAcceso>> listar() {
        return ResponseEntity.ok(eventoAccesoRepository.findAll());
    }
}
