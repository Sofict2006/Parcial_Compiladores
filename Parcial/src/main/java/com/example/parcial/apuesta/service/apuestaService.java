package com.example.parcial.apuesta.service;

import com.example.parcial.apuesta.dto.apuestaCreateDTO;
import com.example.parcial.apuesta.repository.apuestaRepository;
import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.apuesta.dto.apuestaResponseDTO;
import com.example.parcial.apuesta.mapper.apuestaMapper;
import com.example.parcial.apuesta.model.apuesta;
import com.example.parcial.pelea.model.pelea;
import com.example.parcial.pelea.repository.peleaRepository;
import com.example.parcial.peleador.model.peleador;
import com.example.parcial.peleador.repository.peleadorRepository;
import com.example.parcial.usuario.model.usuario;
import com.example.parcial.usuario.repository.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class apuestaService {

    private final apuestaRepository apuestaRepository;
    private final peleadorRepository peleadorRepository;
    private final peleaRepository peleaRepository;
    private final usuarioRepository usuarioRepository;

    //Este constructor asi diferente es para traerme el repositorio de peleador, pelea y usuario y poder encontrar el objeto para meterlo en la pelea
    public apuestaService(peleaRepository peleaRepository, peleadorRepository peleadorRepository1,
                          apuestaRepository apuestaRepository1, usuarioRepository usuarioRepository) {
        this.peleaRepository = peleaRepository;
        this.peleadorRepository = peleadorRepository1;
        this.apuestaRepository = apuestaRepository1;
        this.usuarioRepository = usuarioRepository;
    }

    public apuestaResponseDTO create(apuestaCreateDTO dto) {
        apuesta apuesta = apuestaMapper.toApuesta(dto);

        // 2. Buscar peleador, usuario, y pelea en BD
        peleador p = peleadorRepository.findById(dto.peleador_id())
                .orElseThrow(() -> new NotFoundException("Peleador no existe"));

        usuario u = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(() -> new NotFoundException("Usuario no existe"));

        pelea pelea = peleaRepository.findById(dto.pelea_id())
                .orElseThrow(() -> new NotFoundException("La pelea no existe"));

        // 3. Asignar relaciones
        apuesta.setPeleador_id(p);
        apuesta.setUsuario_id(u);
        apuesta.setPelea_id(pelea);

        //Para saber si el usuario gano o no
        if ( pelea.getGanador().getId().equals( p.getId() ) ) {
            apuesta.setGano(true);
        } else {
            apuesta.setGano(false);
        }

        apuesta saved = apuestaRepository.save(apuesta);
        return apuestaMapper.toApuestaResponseDTO(saved);
    }

    public List<apuestaResponseDTO> findAll() {
        return apuestaRepository.findAll().stream()
                .map(apuestaMapper::toApuestaResponseDTO)
                .toList();
    }

    public apuestaResponseDTO findById(Long id){
        apuesta apuesta = apuestaRepository.findById(id)
                .orElseThrow( ()->new NotFoundException("Apuesta no encontrada"));
        return apuestaMapper.toApuestaResponseDTO(apuesta);
    }

}
