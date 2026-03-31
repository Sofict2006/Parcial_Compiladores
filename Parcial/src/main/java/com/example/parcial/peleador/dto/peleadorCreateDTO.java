package com.example.parcial.peleador.dto;

import jakarta.validation.constraints.*;

public record peleadorCreateDTO(
        //Campos del peleador con el mismo nombre que en el model

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=120)
        String nombre,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=200)
        String biografia,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=200)
        String habilidad_especial,

        @NotNull
        @Min(1)
        @Max(100)
        Integer fuerza,

        @NotNull
        @Min(1)
        @Max(100)
        Integer velocidad,

        @NotNull
        @Min(1)
        @Max(100)
        Integer resistencia_ataques,

        @NotNull
        @Min(1)
        @Max(100)
        Integer vida
) {
}
