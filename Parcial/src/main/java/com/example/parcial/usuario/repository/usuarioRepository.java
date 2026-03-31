package com.example.parcial.usuario.repository;

import com.example.parcial.usuario.model.usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface usuarioRepository extends JpaRepository<usuario, Long> {
}
