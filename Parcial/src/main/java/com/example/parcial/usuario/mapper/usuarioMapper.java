package com.example.parcial.usuario.mapper;

import com.example.parcial.usuario.dto.usuarioResponseDTO;
import com.example.parcial.usuario.model.usuario;

import com.example.parcial.usuario.dto.usuarioCreateDTO;

public class usuarioMapper {

    private usuarioMapper() {
    }
    public static usuarioResponseDTO toUsuarioResponseDTO(usuario usuario) {
        return new usuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getBiografia(),
                usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.getRol()

        );
    }

    public static usuario toUsuario(usuarioCreateDTO dto) {
        usuario usuario = new usuario();

        usuario.setNombre(dto.nombre());
        usuario.setBiografia(dto.biografia());
        usuario.setCorreo(dto.correo());
        usuario.setContrasena(dto.contrasena());
        usuario.setRol(dto.rol());

        return usuario;
    }

}
