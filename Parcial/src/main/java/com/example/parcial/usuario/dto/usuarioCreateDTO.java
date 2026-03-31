package com.example.parcial.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record usuarioCreateDTO(

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=120)
        String nombre,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=200)
        String biografia,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=200)
        String correo,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=50)
        String contrasena,

        @NotBlank(message = "Este campo debe contener informacion")
        @Size(min=1, max=50)
        String rol

) {
}
