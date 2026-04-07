package com.example.parcial.usuario.service;

import com.example.parcial.excepciones.CorreoDuplicadoException;
import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.usuario.dto.usuarioCreateDTO;
import com.example.parcial.usuario.dto.usuarioResponseDTO;
import com.example.parcial.usuario.mapper.usuarioMapper;
import com.example.parcial.usuario.model.usuario;
import com.example.parcial.usuario.repository.usuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class usuarioService {

    private final usuarioRepository usuarioRepository;

    public usuarioService(usuarioRepository usuarioRepository1) {
        this.usuarioRepository = usuarioRepository1;
    }

    public usuarioResponseDTO create(usuarioCreateDTO dto) {
        String correoNormalizado = dto.correo().trim().toLowerCase();

        if (usuarioRepository.existsByCorreoIgnoreCase(correoNormalizado)) {
            throw new CorreoDuplicadoException("Ya existe un usuario con ese correo");
        }

        usuario usuario = usuarioMapper.toUsuario(dto);
        usuario.setCorreo(correoNormalizado);

        usuario saved = usuarioRepository.save(usuario);
        return usuarioMapper.toUsuarioResponseDTO(saved);
    }

    public List<usuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toUsuarioResponseDTO)
                .toList();
    }

    public usuarioResponseDTO findById(Long id) {
        usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return usuarioMapper.toUsuarioResponseDTO(usuario);
    }
}