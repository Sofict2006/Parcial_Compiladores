package com.example.parcial.apuesta.model;

import com.example.parcial.pelea.model.pelea;
import com.example.parcial.peleador.model.peleador;
import com.example.parcial.usuario.model.usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name="apuesta")
//Crea getters, setters y constructor
@Data
public class apuesta {

    //Creo el campo id y lo genero solito
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private usuario usuario_id;

    @ManyToOne
    @JoinColumn(name = "peleador_id", nullable = false)
    private peleador peleador_id;

    @ManyToOne
    @JoinColumn(name = "pelea_id", nullable = false)
    private pelea pelea_id;

    @Column(nullable = false)
    private Double monto;

    @Column
    private Boolean gano;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        apuesta apuesta = (apuesta) o;
        return Objects.equals(id, apuesta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
