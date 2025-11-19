package com.sicae.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sicae.dto.PuntoAccesoRequest;
import com.sicae.model.PuntoAcceso;
import com.sicae.repository.PuntoAccesoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PuntoAccesoService {
    private final PuntoAccesoRepository puntoAccesoRepository;

    public PuntoAcceso registrar(PuntoAccesoRequest request) {
        PuntoAcceso punto = PuntoAcceso.builder()
                .nombre(request.nombre())
                .ubicacion(request.ubicacion())
                .tipo(request.tipo())
                .activo(request.activo())
                .fechaInstalacion(Instant.now())
                .build();
        return puntoAccesoRepository.save(punto);
    }

    public List<PuntoAcceso> listar() {
        return puntoAccesoRepository.findAll();
    }
}
