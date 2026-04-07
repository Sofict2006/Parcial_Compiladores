-- ============================================
-- Datos de prueba para el sistema de peleas
-- Se insertan solo si no existen (evita duplicados al reiniciar)
-- ============================================

-- PELEADORES
INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 1, 'Goku', 'Saiyajin criado en la Tierra, protector del universo', 'Kamehameha', 95, 90, 85, 100
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 1);

INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 2, 'Vegeta', 'Principe de los Saiyajin, rival eterno de Goku', 'Final Flash', 92, 88, 80, 95
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 2);

INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 3, 'Naruto', 'Ninja de la Aldea de la Hoja, jinchuriki del Kyubi', 'Rasengan', 85, 92, 78, 90
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 3);

INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 4, 'Luffy', 'Capitan de los Sombrero de Paja, usuario de la Gomu Gomu', 'Gear Fifth', 88, 85, 90, 88
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 4);

INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 5, 'Saitama', 'Heroe por hobby, derrota a todos de un solo golpe', 'One Punch', 100, 70, 100, 100
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 5);

INSERT INTO peleador (id, nombre, biografia, habilidad_especial, fuerza, velocidad, resistencia_ataques, vida)
SELECT 6, 'Gojo Satoru', 'El hechicero mas fuerte de Jujutsu Kaisen', 'Dominio Infinito', 93, 95, 88, 92
WHERE NOT EXISTS (SELECT 1 FROM peleador WHERE id = 6);

-- USUARIOS
INSERT INTO usuario (id, nombre, biografia, correo, contrasena, rol)
SELECT 1, 'Carlos Admin', 'Administrador del sistema de peleas', 'admin@peleas.com', 'admin123', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE id = 1);

INSERT INTO usuario (id, nombre, biografia, correo, contrasena, rol)
SELECT 2, 'Maria Apostadora', 'Fan de las peleas y las apuestas', 'maria@peleas.com', 'maria123', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE id = 2);

INSERT INTO usuario (id, nombre, biografia, correo, contrasena, rol)
SELECT 3, 'Juan Espectador', 'Le encanta ver las peleas', 'juan@peleas.com', 'juan123', 'USER'
WHERE NOT EXISTS (SELECT 1 FROM usuario WHERE id = 3);

-- PELEAS
INSERT INTO pelea (id, peleador1_id, peleador2_id, ganador_id, fecha, comentarios)
SELECT 1, 1, 2, 1, '2026-04-01', 'Pelea epica entre Saiyajines! Goku gano con un Kamehameha final'
WHERE NOT EXISTS (SELECT 1 FROM pelea WHERE id = 1);

INSERT INTO pelea (id, peleador1_id, peleador2_id, ganador_id, fecha, comentarios)
SELECT 2, 3, 4, 4, '2026-04-02', 'Naruto vs Luffy! El Gear Fifth fue demasiado para Naruto'
WHERE NOT EXISTS (SELECT 1 FROM pelea WHERE id = 2);

INSERT INTO pelea (id, peleador1_id, peleador2_id, ganador_id, fecha, comentarios)
SELECT 3, 5, 6, 5, '2026-04-03', 'Saitama vs Gojo... un solo golpe fue suficiente'
WHERE NOT EXISTS (SELECT 1 FROM pelea WHERE id = 3);

-- APUESTAS
INSERT INTO apuesta (id, usuario_id, peleador_id, pelea_id, monto, gano)
SELECT 1, 2, 1, 1, 50000.0, true
WHERE NOT EXISTS (SELECT 1 FROM apuesta WHERE id = 1);

INSERT INTO apuesta (id, usuario_id, peleador_id, pelea_id, monto, gano)
SELECT 2, 3, 2, 1, 30000.0, false
WHERE NOT EXISTS (SELECT 1 FROM apuesta WHERE id = 2);

INSERT INTO apuesta (id, usuario_id, peleador_id, pelea_id, monto, gano)
SELECT 3, 2, 4, 2, 75000.0, true
WHERE NOT EXISTS (SELECT 1 FROM apuesta WHERE id = 3);

INSERT INTO apuesta (id, usuario_id, peleador_id, pelea_id, monto, gano)
SELECT 4, 3, 5, 3, 100000.0, true
WHERE NOT EXISTS (SELECT 1 FROM apuesta WHERE id = 4);

-- Resetear secuencias para que el proximo INSERT auto-generado no colisione
SELECT setval('peleador_id_seq', (SELECT COALESCE(MAX(id),0) FROM peleador));
SELECT setval('usuario_id_seq', (SELECT COALESCE(MAX(id),0) FROM usuario));
SELECT setval('pelea_id_seq', (SELECT COALESCE(MAX(id),0) FROM pelea));
SELECT setval('apuesta_id_seq', (SELECT COALESCE(MAX(id),0) FROM apuesta));
