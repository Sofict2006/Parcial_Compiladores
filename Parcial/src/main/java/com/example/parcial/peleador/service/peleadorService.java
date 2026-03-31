package com.example.parcial.peleador.service;

import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.peleador.dto.peleadorCreateDTO;
import com.example.parcial.peleador.dto.peleadorResponseDTO;
import com.example.parcial.peleador.mapper.peleadorMapper;
import com.example.parcial.peleador.model.peleador;
import com.example.parcial.peleador.repository.peleadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Recibe info del controlador y va al repositorio
@Service

public class peleadorService {

    private final peleadorRepository peleadorRepository;
    public peleadorService(peleadorRepository peleadorRepository1) {
        this.peleadorRepository = peleadorRepository1;
    }

    public peleadorResponseDTO create(peleadorCreateDTO dto) {
        peleador peleador = peleadorMapper.toPeleador(dto);
        peleador saved = peleadorRepository.save(peleador);
        return peleadorMapper.toPeleadorResponseDTO(saved);
    }

    public List<peleadorResponseDTO> findAll() {
        return peleadorRepository.findAll().stream()
                .map(peleadorMapper::toPeleadorResponseDTO)
                .toList();
    }

    public peleadorResponseDTO findById(Long id){
        peleador peleador = peleadorRepository.findById(id)
                .orElseThrow( ()->new NotFoundException("Peleador no encontrado"));
        return peleadorMapper.toPeleadorResponseDTO(peleador);
    }

}
