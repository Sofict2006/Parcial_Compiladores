package com.example.parcial.apuesta.repository;

import com.example.parcial.apuesta.model.apuesta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface apuestaRepository extends JpaRepository<apuesta, Long>  {
}
