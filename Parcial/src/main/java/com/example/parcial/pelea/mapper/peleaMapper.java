package com.example.parcial.pelea.mapper;

import com.example.parcial.pelea.dto.peleaResponseDTO;
import com.example.parcial.pelea.model.pelea;
import com.example.parcial.pelea.dto.peleaCreateDTO;

public class peleaMapper {

    private peleaMapper() {
    }
    public static peleaResponseDTO toPeleaResponseDTO(pelea pelea) {
        return new peleaResponseDTO(
                pelea.getId(),
                pelea.getPeleador1().getId(),
                pelea.getPeleador2().getId(),
                //Un if chiquitin por si el ganador es nulo
                pelea.getGanador() != null ? pelea.getGanador().getId() : null,
                pelea.getFecha(),
                pelea.getComentarios()
        );
    }

    public static pelea toPelea(peleaCreateDTO dto) {
        pelea pelea = new pelea();

        pelea.setFecha(dto.fecha());
        pelea.setComentarios(dto.comentarios());

        return pelea;
    }

}
