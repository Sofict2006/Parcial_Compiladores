package com.example.parcial.peleador.dto;

public record peleadorResponseDTO(
        Long id,
        String nombre,
        String biografia,
        String habilidad_especial,
        Integer fuerza,
        Integer velocidad,
        Integer resistencia_ataques,
        Integer vida
) {
}
