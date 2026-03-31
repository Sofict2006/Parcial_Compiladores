package com.example.parcial.peleador.controller;

import com.example.parcial.peleador.dto.peleadorCreateDTO;
import com.example.parcial.peleador.dto.peleadorResponseDTO;
import com.example.parcial.peleador.service.peleadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peleadores")

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
    public peleadorResponseDTO create(@Valid @RequestBody peleadorCreateDTO dto) {
        return peleadorService.create(dto);
    }

}
