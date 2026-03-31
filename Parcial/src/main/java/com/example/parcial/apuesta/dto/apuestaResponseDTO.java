package com.example.parcial.apuesta.dto;

import java.util.Date;

public record apuestaResponseDTO(
        Long id,
        Long usuario_id,
        Long peleador_id,
        Long pelea_id,
        Double monto,
        Boolean gano
) {
}
