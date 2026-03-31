package com.example.parcial.pelea.dto;

import java.util.Date;

public record peleaResponseDTO(
        Long id,
        Long peleador1_id,
        Long peleador2_id,
        Long ganador_id,
        Date fecha,
        String comentarios
) {
}
