package com.example.parcial.usuario.model;

import com.example.parcial.pelea.model.pelea;
import com.example.parcial.peleador.model.peleador;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Entity
@Table(
        name = "usuario",
        uniqueConstraints = @UniqueConstraint(columnNames = "correo")
)
//Crea getters, setters y constructor
@Data
public class usuario {

    //Creo el campo id y lo genero solito
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String biografia;

    @Column(nullable = false, length = 200, unique = true) //Debe ser unico para cada usuario 
    private String correo;

    @Column(nullable = false, length = 50)
    private String contrasena;

    @Column(nullable = false, length = 50)
    private String rol;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        usuario usuario = (usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
