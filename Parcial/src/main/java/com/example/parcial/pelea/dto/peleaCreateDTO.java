package com.example.parcial.pelea.dto;

import jakarta.validation.constraints.*;

import java.util.Date;

public record peleaCreateDTO(

        @NotNull(message = "El id del peleador 1 es obligatorio")
        Long peleador1_id,

        @NotNull(message = "El id del peleador 2 es obligatorio")
        Long peleador2_id,

        //Puede no haber ganador
        Long ganador_id,

        @NotNull(message = "La fecha es obligatoria")
        Date fecha,

        String comentarios
) {
}
