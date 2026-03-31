package com.example.parcial.usuario.controller;

import com.example.parcial.usuario.dto.usuarioCreateDTO;
import com.example.parcial.usuario.dto.usuarioResponseDTO;
import com.example.parcial.usuario.service.usuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class usuarioController {

    private final usuarioService usuarioService;
    public usuarioController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<usuarioResponseDTO> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public usuarioResponseDTO findById(@PathVariable Long id) {
        return usuarioService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public usuarioResponseDTO create(@Valid @RequestBody usuarioCreateDTO dto) {
        return usuarioService.create(dto);
    }
}
