package com.example.parcial.pelea.model;

import com.example.parcial.peleador.model.peleador;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.util.*;

@Entity
@Table(name = "pelea")
@NoArgsConstructor
@Data
public class pelea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Primer peleador
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "peleador1_id", nullable = false)
    private peleador peleador1;

    // Segundo peleador
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "peleador2_id", nullable = false)
    private peleador peleador2;

    @ManyToOne
    @JoinColumn(name = "ganador_id")
    private peleador ganador;

    @Column(nullable = false)
    private Date fecha;

    @Column
    private String comentarios;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        pelea pelea = (pelea) o;
        return Objects.equals(id, pelea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
