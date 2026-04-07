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
//Excepciones:
import com.example.parcial.excepciones.MontoInvalidoException; //alfabetico o numerico no 
import com.example.parcial.excepciones.PeleaNoEncontradaException;
import com.example.parcial.excepciones.PeleadorNoEncontradoException;
import com.example.parcial.excepciones.PeleadorNoPerteneceAPeleaException; //apostar solo por los peleadores de esa pelea
import com.example.parcial.excepciones.UsuarioNoEncontradoException;

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

        if (dto.monto() == null || dto.monto() <= 0) {
            throw new MontoInvalidoException("El monto de la apuesta debe ser mayor que 0");
        }

        peleador p = peleadorRepository.findById(dto.peleador_id())
                .orElseThrow(() -> new PeleadorNoEncontradoException("Peleador no existe"));

        usuario u = usuarioRepository.findById(dto.usuario_id())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no existe"));

        pelea pelea = peleaRepository.findById(dto.pelea_id())
                .orElseThrow(() -> new PeleaNoEncontradaException("La pelea no existe"));

        boolean perteneceAPelea =
                Objects.equals(pelea.getPeleador1().getId(), p.getId()) ||
                Objects.equals(pelea.getPeleador2().getId(), p.getId());

        if (!perteneceAPelea) {
            throw new PeleadorNoPerteneceAPeleaException(
                    "El peleador apostado no pertenece a esta pelea"
            );
        }

        apuesta apuesta = apuestaMapper.toApuesta(dto);

        apuesta.setPeleador_id(p);
        apuesta.setUsuario_id(u);
        apuesta.setPelea_id(pelea);

        if (pelea.getGanador().getId().equals(p.getId())) {
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
