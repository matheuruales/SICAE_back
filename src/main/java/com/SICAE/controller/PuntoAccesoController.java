package com.sicae.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicae.dto.PuntoAccesoRequest;
import com.sicae.model.PuntoAcceso;
import com.sicae.service.PuntoAccesoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/puntos-acceso")
@RequiredArgsConstructor
public class PuntoAccesoController {
    private final PuntoAccesoService puntoAccesoService;

    @PostMapping
    public ResponseEntity<PuntoAcceso> registrar(@Validated @RequestBody PuntoAccesoRequest request) {
        return ResponseEntity.ok(puntoAccesoService.registrar(request));
    }

    @GetMapping
    public ResponseEntity<List<PuntoAcceso>> listar() {
        return ResponseEntity.ok(puntoAccesoService.listar());
    }
}
