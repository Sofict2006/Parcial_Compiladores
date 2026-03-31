package com.example.parcial.pelea.repository;

import com.example.parcial.pelea.model.pelea;
import com.example.parcial.peleador.model.peleador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface peleaRepository extends JpaRepository<pelea, Long> {

    public pelea findByGanador_id(String ganador_id);
    //@Query("Asi se consulta, por si necesito alguna")

}
