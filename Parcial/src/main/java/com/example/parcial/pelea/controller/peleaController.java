package com.example.parcial.pelea.controller;

import com.example.parcial.pelea.dto.peleaCreateDTO;
import com.example.parcial.pelea.dto.peleaResponseDTO;
import com.example.parcial.pelea.service.peleaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/pelea")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Por defecto todos pueden ver
public class peleaController {

    private final peleaService peleaService;

    public peleaController(peleaService peleaService) {
        this.peleaService = peleaService;
    }

    @GetMapping
    public List<peleaResponseDTO> findAll() {
        return peleaService.findAll();
    }

    @GetMapping("/{id}")
    public peleaResponseDTO findById(@PathVariable Long id) {
        return peleaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')") // Solo Simón Maligno puede crear esto
    public peleaResponseDTO create(@Valid @RequestBody peleaCreateDTO dto) {
        return peleaService.create(dto);
    }
}
