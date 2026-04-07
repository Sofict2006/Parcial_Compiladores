package com.example.parcial.peleador.controller;

import com.example.parcial.peleador.dto.peleadorCreateDTO;
import com.example.parcial.peleador.dto.peleadorResponseDTO;
import com.example.parcial.peleador.service.peleadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/peleadores")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')") // Por defecto todos pueden ver
public class peleadorController {

    private final peleadorService peleadorService;

    public peleadorController(peleadorService peleadorService) {
        this.peleadorService = peleadorService;
    }

    @GetMapping
    public List<peleadorResponseDTO> findAll() {
        return peleadorService.findAll();
    }

    @GetMapping("/{id}")
    public peleadorResponseDTO findById(@PathVariable Long id) {
        return peleadorService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')") // Solo Simón Maligno puede crear esto
    public peleadorResponseDTO create(@Valid @RequestBody peleadorCreateDTO dto) {
        return peleadorService.create(dto);
    }

}
