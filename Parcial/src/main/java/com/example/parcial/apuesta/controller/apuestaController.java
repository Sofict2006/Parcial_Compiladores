package com.example.parcial.apuesta.controller;

import com.example.parcial.apuesta.dto.apuestaCreateDTO;
import com.example.parcial.apuesta.dto.apuestaResponseDTO;
import com.example.parcial.apuesta.service.apuestaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/apuesta")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class apuestaController {

    private final apuestaService apuestaService;

    public apuestaController(apuestaService apuestaService) {
        this.apuestaService = apuestaService;
    }

    @GetMapping
    public List<apuestaResponseDTO> findAll() {
        return apuestaService.findAll();
    }

    @GetMapping("/{id}")
    public apuestaResponseDTO findById(@PathVariable Long id) {
        return apuestaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public apuestaResponseDTO create(@Valid @RequestBody apuestaCreateDTO dto) {
        return apuestaService.create(dto);
    }
}
