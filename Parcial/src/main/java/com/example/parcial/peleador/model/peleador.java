package com.example.parcial.peleador.model;

import com.example.parcial.pelea.model.pelea;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="peleador")
//Crea getters, setters y constructor
@Data

public class peleador {

    //Creo el campo id y lo genero solito
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String biografia;

    @Column(nullable = false, length = 200)
    private String habilidad_especial;

    @Column(nullable = false)
    private Integer fuerza;

    @Column(nullable = false)
    private Integer velocidad;

    @Column(nullable = false)
    private Integer resistencia_ataques;

    @Column(nullable = false)
    private Integer vida;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        peleador peleador = (peleador) o;
        return Objects.equals(id, peleador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
