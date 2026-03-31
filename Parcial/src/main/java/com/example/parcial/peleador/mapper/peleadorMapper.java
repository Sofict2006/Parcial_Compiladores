package com.example.parcial.peleador.mapper;

import com.example.parcial.peleador.dto.peleadorCreateDTO;
import com.example.parcial.peleador.dto.peleadorResponseDTO;
import com.example.parcial.peleador.model.peleador;

public class peleadorMapper {

    private peleadorMapper() {
    }
    public static peleadorResponseDTO toPeleadorResponseDTO(peleador peleador) {
        return new peleadorResponseDTO(
                peleador.getId(),
                peleador.getNombre(),
                peleador.getBiografia(),
                peleador.getHabilidad_especial(),
                peleador.getFuerza(),
                peleador.getVelocidad(),
                peleador.getResistencia_ataques(),
                peleador.getVida()
        );
    }

    public static peleador toPeleador(peleadorCreateDTO dto) {
        peleador peleadorcin = new peleador();

        peleadorcin.setNombre(dto.nombre());
        peleadorcin.setBiografia(dto.biografia());
        peleadorcin.setHabilidad_especial(dto.habilidad_especial());
        peleadorcin.setFuerza(dto.fuerza());
        peleadorcin.setVelocidad(dto.velocidad());
        peleadorcin.setResistencia_ataques(dto.resistencia_ataques());
        peleadorcin.setVida(dto.vida());

        return peleadorcin;
    }

}
