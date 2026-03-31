package com.example.parcial.usuario.service;

import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.usuario.dto.usuarioCreateDTO;
import com.example.parcial.usuario.dto.usuarioResponseDTO;
import com.example.parcial.usuario.mapper.usuarioMapper;
import com.example.parcial.usuario.model.usuario;
import com.example.parcial.usuario.repository.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//Recibe info del controlador y va al repositorio
@Service
public class usuarioService {

    private final usuarioRepository usuarioRepository;
    public usuarioService(usuarioRepository usuarioRepository1) {
        this.usuarioRepository = usuarioRepository1;
    }

    public usuarioResponseDTO create(usuarioCreateDTO dto) {
        usuario usuario = usuarioMapper.toUsuario(dto);
        usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioResponseDTO(saved);
    }

    public List<usuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toUsuarioResponseDTO)
                .toList();
    }

    public usuarioResponseDTO findById(Long id){
        usuario usuario = usuarioRepository.findById(id)
                .orElseThrow( ()->new NotFoundException("Usuario no encontrado"));
        return usuarioMapper.toUsuarioResponseDTO(usuario);
    }


}
