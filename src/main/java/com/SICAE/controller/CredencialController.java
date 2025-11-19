package com.sicae.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sicae.dto.GenerarQrRequest;
import com.sicae.dto.ValidarQrRequest;
import com.sicae.model.Credencial;
import com.sicae.model.EventoAcceso;
import com.sicae.service.CredencialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/credenciales")
@RequiredArgsConstructor
public class CredencialController {

    private final CredencialService credencialService;

    @PostMapping("/qr")
    public ResponseEntity<Credencial> generarQr(@Validated @RequestBody GenerarQrRequest request) {
        return ResponseEntity.ok(credencialService.generarQr(request));
    }

    @GetMapping
    public ResponseEntity<List<Credencial>> listar() {
        return ResponseEntity.ok(credencialService.listar());
    }

    @PostMapping("/validar")
    public ResponseEntity<EventoAcceso> validar(@Validated @RequestBody ValidarQrRequest request) {
        return ResponseEntity.ok(credencialService.validarQr(request));
    }

    @PostMapping("/revocar/{id}")
    public ResponseEntity<Credencial> revocar(@PathVariable String id, @RequestBody(required = false) Map<String, String> body) {
        String motivo = body != null ? body.getOrDefault("motivo", "Revocado manualmente") : "Revocado manualmente";
        return ResponseEntity.ok(credencialService.revocar(id, motivo));
    }
}
