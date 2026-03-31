package com.example.parcial.apuesta.mapper;

import com.example.parcial.apuesta.dto.apuestaResponseDTO;
import com.example.parcial.apuesta.model.apuesta;
import com.example.parcial.apuesta.dto.apuestaCreateDTO;

public class apuestaMapper {

    private apuestaMapper() {
    }
    public static apuestaResponseDTO toApuestaResponseDTO(apuesta apuesta) {
        return new apuestaResponseDTO(
                apuesta.getId(),
                apuesta.getUsuario_id().getId(),
                apuesta.getPeleador_id().getId(),
                apuesta.getPelea_id().getId(),
                apuesta.getMonto(),
                apuesta.getGano()
        );
    }

    public static apuesta toApuesta(apuestaCreateDTO dto) {
        apuesta apuesta = new apuesta();

        apuesta.setMonto(dto.monto());

        return apuesta;
    }

}
