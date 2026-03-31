package com.example.parcial.peleador.repository;

import com.example.parcial.pelea.model.pelea;
import com.example.parcial.peleador.model.peleador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

//Crea las funciones de insertar, eliminar, y todas dice el profesor

public interface peleadorRepository
        //JpaRepository<Entidad, TipoID>
        extends JpaRepository<peleador, Long> {

    //ponerlo en pelea
    //public List<pelea> findByPeleador1OrPeleador2(peleador p1, peleador p2);

    public peleador findByNombre(String nombre);
    //@Query("Asi se consulta, por si necesito alguna")

}
