package com.example.parcial.pelea.service;

import com.example.parcial.excepciones.NotFoundException;
import com.example.parcial.pelea.dto.peleaCreateDTO;
import com.example.parcial.pelea.dto.peleaResponseDTO;
import com.example.parcial.pelea.model.pelea;
import com.example.parcial.pelea.repository.peleaRepository;
import com.example.parcial.pelea.mapper.peleaMapper;
import com.example.parcial.peleador.model.peleador;
import com.example.parcial.peleador.repository.peleadorRepository;
import org.springframework.stereotype.Service;
//Los de las excepciones que existan y que no esten repetidos (pues no puedo pelear conmigo misma, o si?)
import com.example.parcial.excepciones.PeleadorNoEncontradoException;
import com.example.parcial.excepciones.PeleadoresRepetidosException;

import java.util.*;

@Service
public class peleaService {

    private final peleadorRepository peleadorRepository;
    private final peleaRepository peleaRepository;

    // Este constructor asi diferente es para traerme el repositorio de peleador y
    // poder encontrar el objeto para meterlo en la pelea
    public peleaService(peleaRepository peleaRepository, peleadorRepository peleadorRepository1) {
        this.peleaRepository = peleaRepository;
        this.peleadorRepository = peleadorRepository1;
    }

    // Creamos la peleita, se buscan los peleadores y se simula el combate
    //Ahora con las excepciones descritas anteriromente 
    public peleaResponseDTO create(peleaCreateDTO dto) {

        if (dto.peleador1_id().equals(dto.peleador2_id())) {
            throw new PeleadoresRepetidosException("No se puede crear una pelea con el mismo peleador dos veces");
        }

        pelea pelea = peleaMapper.toPelea(dto);

        peleador p1 = peleadorRepository.findById(dto.peleador1_id())
                .orElseThrow(() -> new PeleadorNoEncontradoException("Peleador 1 no encontrado"));

        peleador p2 = peleadorRepository.findById(dto.peleador2_id())
                .orElseThrow(() -> new PeleadorNoEncontradoException("Peleador 2 no encontrado"));

        pelea.setPeleador1(p1);
        pelea.setPeleador2(p2);

        Random random = new Random();

        int vida1 = p1.getVida();
        int vida2 = p2.getVida();
        boolean segundaOportunidadUsada1 = false;
        boolean segundaOportunidadUsada2 = false;

        StringBuilder sb = new StringBuilder();
        int ronda = 1;

        while (vida1 > 0 && vida2 > 0) {

            int iniciativa1 = p1.getVelocidad() + random.nextInt(20);
            int iniciativa2 = p2.getVelocidad() + random.nextInt(20);
            peleador atacante, defensor;
            int vidaDefensor;
            boolean esP1Primero = iniciativa1 >= iniciativa2;

            atacante = esP1Primero ? p1 : p2;
            defensor = esP1Primero ? p2 : p1;
            vidaDefensor = esP1Primero ? vida2 : vida1;

            vidaDefensor = resolverAtaque(atacante, defensor, vidaDefensor, random, sb, ronda);

            boolean usada = esP1Primero ? segundaOportunidadUsada2 : segundaOportunidadUsada1;
            if (vidaDefensor <= 0 && !usada && random.nextDouble() < 0.15) {
                int vidaRecuperada = (int) (defensor.getVida() * 0.2);
                vidaDefensor = vidaRecuperada;
                if (sb.length() < 900) {
                    sb.append("💀➡️🔥¡").append(defensor.getNombre())
                            .append(" se niega a caer! ¡Se levanta con furia recuperando ")
                            .append(vidaRecuperada).append(" de vida! ");
                }
                if (esP1Primero)
                    segundaOportunidadUsada2 = true;
                else
                    segundaOportunidadUsada1 = true;
            }

            if (esP1Primero) {
                vida2 = vidaDefensor;
            } else {
                vida1 = vidaDefensor;
            }

            if (vidaDefensor <= 0)
                break;

            atacante = esP1Primero ? p2 : p1;
            defensor = esP1Primero ? p1 : p2;
            vidaDefensor = esP1Primero ? vida1 : vida2;

            vidaDefensor = resolverAtaque(atacante, defensor, vidaDefensor, random, sb, ronda);

            usada = esP1Primero ? segundaOportunidadUsada1 : segundaOportunidadUsada2;
            if (vidaDefensor <= 0 && !usada && random.nextDouble() < 0.15) {
                int vidaRecuperada = (int) (defensor.getVida() * 0.2);
                vidaDefensor = vidaRecuperada;
                if (sb.length() < 900) {
                    sb.append("💀➡️🔥 ¡").append(defensor.getNombre())
                            .append(" renace con pura voluntad y recupera ")
                            .append(vidaRecuperada).append(" de vida! ");
                }
                if (esP1Primero)
                    segundaOportunidadUsada1 = true;
                else
                    segundaOportunidadUsada2 = true;
            }

            if (esP1Primero) {
                vida1 = vidaDefensor;
            } else {
                vida2 = vidaDefensor;
            }

            ronda++;
            if (ronda > 100)
                break;
        }

        peleador ganador;
        if (vida1 > vida2)
            ganador = p1;
        else if (vida2 > vida1)
            ganador = p2;
        else
            ganador = random.nextBoolean() ? p1 : p2;

        sb.append("🏆 Ganador: ").append(ganador.getNombre()).append("!");
        pelea.setGanador(ganador);
        pelea.setComentarios(sb.toString());

        pelea saved = peleaRepository.save(pelea);
        return peleaMapper.toPeleaResponseDTO(saved);
    }

    /**
     * Resuelve un ataque individual con todos los factores:
     * - Fuerza → daño base
     * - Velocidad → probabilidad de esquivar
     * - Resistencia_ataques → reduce el daño recibido
     * - Eventos aleatorios creativos: golpe crítico, combo, contraataque, esquiva
     */
    private int resolverAtaque(peleador atacante, peleador defensor, int vidaDefensor,
            Random random, StringBuilder sb, int ronda) {

        // Si el defensor es rapido puede esquivar, maximo como un 30% de proba asi que estamos melos
        double probEsquivar = Math.min(0.30, defensor.getVelocidad() / 350.0);
        if (random.nextDouble() < probEsquivar) {
            if (sb.length() < 900) {
                sb.append("💨 ¡").append(defensor.getNombre())
                        .append(" esquivó el ataque de ").append(atacante.getNombre())
                        .append(" con reflejos felinos! ");
            }
            // Y si esquiva, hay un 10% de que le devuelva el puño
            if (random.nextDouble() < 0.10) {
                int danoContra = (int) (atacante.getFuerza() * 0.5 * random.nextDouble());
                vidaDefensor += 0; // el defensor no recibe daño
                if (sb.length() < 900) {
                    sb.append("⚡ ¡").append(defensor.getNombre())
                            .append(" contraatacó en el acto causando ")
                            .append(danoContra).append(" de daño! ");
                }
                // El contraataque en teoria daña al atacante, pero como aqui no tengo
                // su vida a la mano, lo dejo como narrativa nomas, el daño real va por turnos
            }
            return vidaDefensor;
        }

        // Calculo cuanto pega: depende de la fuerza y la suelte
        int danoBase = (int) (atacante.getFuerza() * (0.5 + random.nextDouble() * 0.7));

        // 12% de proba de golpe critico, asi como en el pokemon si sabe ja
        boolean critico = random.nextDouble() < 0.12;
        if (critico) {
            danoBase = (int) (danoBase * 1.8);
        }

        // 8% de proba de hacer combo, una rafaga de golpes donde la velocidad tambien afecta 
        // pq son artos golpes rapido si sabe ja
        boolean combo = !critico && random.nextDouble() < 0.08;
        if (combo) {
            int golpesExtra = 1 + random.nextInt(3); // le mete de 1 a 3 golpes extras
            int danoCombo = 0;
            for (int i = 0; i < golpesExtra; i++) {
                danoCombo += (int) (atacante.getFuerza() * 0.3 * random.nextDouble())
                        + (int) (atacante.getVelocidad() * 0.1);
            }
            danoBase += danoCombo;
            if (sb.length() < 900) {
                sb.append("🔥 ¡").append(atacante.getNombre())
                        .append(" lanzó un COMBO de ").append(golpesExtra + 1)
                        .append(" golpes! ");
            }
        }

        // La resistencia del defensor le baja el daño, maximo le quita como un 40%
        double reduccion = Math.min(0.40, defensor.getResistencia_ataques() / 250.0);
        // Pero si fue critico, la resistencia solo cuenta a medias asi melos
        if (critico)
            reduccion *= 0.5;
        int danoFinal = Math.max(1, (int) (danoBase * (1.0 - reduccion)));

        vidaDefensor -= danoFinal;

        // Armo la narracion dependiendo de que paso en el golpe
        if (sb.length() < 900) {
            if (critico) {
                sb.append("💥 ¡GOLPE CRÍTICO! ").append(atacante.getNombre())
                        .append(" destroza a ").append(defensor.getNombre())
                        .append(" con ").append(danoFinal).append(" de daño! ");
            } else if (!combo) {
                sb.append("👊 ").append(atacante.getNombre())
                        .append(" golpea a ").append(defensor.getNombre())
                        .append(" causando ").append(danoFinal).append(" de daño. ");
            } else {
                sb.append("Total combo: ").append(danoFinal).append(" de daño a ")
                        .append(defensor.getNombre()).append("! ");
            }
        }

        return vidaDefensor;
    }

    // Me traigo todas las peleas de la BD y las convierto a DTOs para devolverlas
    public List<peleaResponseDTO> findAll() {
        return peleaRepository.findAll().stream()
                .map(peleaMapper::toPeleaResponseDTO)
                .toList();
    }

    // Busco una pelea por su id, si no la encuentra pues mando error
    public peleaResponseDTO findById(Long id) {
        pelea pelea = peleaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pelea no encontrada"));
        return peleaMapper.toPeleaResponseDTO(pelea);
    }
}
