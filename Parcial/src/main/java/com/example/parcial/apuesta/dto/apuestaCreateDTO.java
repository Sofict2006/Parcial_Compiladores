package com.example.parcial.apuesta.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record apuestaCreateDTO(
        @NotNull(message = "El id del usuario es obligatorio")
        Long usuario_id,

        @NotNull(message = "El id del peleador es obligatorio")
        Long peleador_id,

        @NotNull(message = "El id de la pelea es obligatorio")
        Long pelea_id,

        @NotNull(message = "El monto a apostar es obligatorio")
        @Positive(message = "El monto debe ser mayor que 0") //Obviamente, numeros negativos nada que ver
        Double monto,

        Boolean gano

) {
}