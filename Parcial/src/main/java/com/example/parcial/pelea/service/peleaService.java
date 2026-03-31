package com.example.parcial.pelea.service;

import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.pelea.dto.peleaCreateDTO;
import com.example.parcial.pelea.dto.peleaResponseDTO;
import com.example.parcial.pelea.model.pelea;
import com.example.parcial.pelea.repository.peleaRepository;
import com.example.parcial.pelea.mapper.peleaMapper;
import com.example.parcial.peleador.model.peleador;
import com.example.parcial.peleador.repository.peleadorRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class peleaService {

    private final peleadorRepository peleadorRepository;
    private final peleaRepository peleaRepository;

    //Este constructor asi diferente es para traerme el repositorio de peleador y poder encontrar el objeto para meterlo en la pelea
    public peleaService(peleaRepository peleaRepository, peleadorRepository peleadorRepository1) {
        this.peleaRepository = peleaRepository;
        this.peleadorRepository = peleadorRepository1;
    }

    /*public peleaResponseDTO create(peleaCreateDTO dto) {
        pelea pelea = peleaMapper.toPelea(dto);

        // 2. Buscar peleadores en BD
        peleador p1 = peleadorRepository.findById(dto.peleador1_id())
                .orElseThrow(() -> new NotFoundException("Peleador 1 no existe"));

        peleador p2 = peleadorRepository.findById(dto.peleador2_id())
                .orElseThrow(() -> new NotFoundException("Peleador 2 no existe"));

        peleador ganador = peleadorRepository.findById(dto.ganador_id())
                .orElseThrow(() -> new NotFoundException("El ganador no existe"));

        // 3. Asignar relaciones
        pelea.setPeleador1(p1);
        pelea.setPeleador2(p2);
        pelea.setGanador(ganador);

        pelea saved = peleaRepository.save(pelea);
        return peleaMapper.toPeleaResponseDTO(saved);
    }*/

    public peleaResponseDTO create(peleaCreateDTO dto) {
        pelea pelea = peleaMapper.toPelea(dto);

        //Traer peleadores desde BD
        peleador p1 = peleadorRepository.findById(dto.peleador1_id())
                .orElseThrow(() -> new NotFoundException("Peleador 1 no encontrado"));

        peleador p2 = peleadorRepository.findById(dto.peleador2_id())
                .orElseThrow(() -> new NotFoundException("Peleador 2 no encontrado"));

        pelea.setPeleador1(p1);
        pelea.setPeleador2(p2);

        //Algoritmo para calcular el ganador
        Random random = new Random();

        int vida1 = p1.getVida();
        int vida2 = p2.getVida();

        String comentarios = "";

        while (vida1 > 0 && vida2 > 0) {

            // daño simple: fuerza + aleatorio
            int dano1 = (int) (p1.getFuerza() * random.nextDouble(1));
            vida2 -= dano1;

            if(comentarios.length() < 200) {
                comentarios += p1.getNombre() + " le hizo " + dano1 + " de daño a " + p2.getNombre() + ". ";
            } else {
                comentarios += " etc.";
            }

            if (vida2 > 0) {
                int dano2 = (int) (p2.getFuerza() * random.nextDouble(1));
                vida1 -= dano2;

                if(comentarios.length() < 200) {
                    comentarios += p2.getNombre() + " le hizo " + dano2 + " de daño a " + p1.getNombre() + ". ";
                }

            }
        }

        peleador ganador = (vida1 > 0) ? p1 : p2;
        comentarios += "Ganador: " + ganador.getNombre();
        pelea.setGanador(ganador);
        pelea.setComentarios(comentarios);

        pelea saved = peleaRepository.save(pelea);
        return peleaMapper.toPeleaResponseDTO(saved);

    }

    public List<peleaResponseDTO> findAll() {
        return peleaRepository.findAll().stream()
                .map(peleaMapper::toPeleaResponseDTO)
                .toList();
    }

    public peleaResponseDTO findById(Long id){
        pelea pelea = peleaRepository.findById(id)
                .orElseThrow( ()->new NotFoundException("Pelea no encontrada"));
        return peleaMapper.toPeleaResponseDTO(pelea);
    }
}
