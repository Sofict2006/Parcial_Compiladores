package com.example.parcial.usuario.dto;

public record usuarioResponseDTO(
        Long id,
        String nombre,
        String biografia,
        String correo,
        String contrasena,
        String rol
) {
}
