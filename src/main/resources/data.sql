-- 1. SUCURSALES (ACTIVO: Tienes la entidad Sucursal)
INSERT IGNORE INTO sucursal (id, nombre, direccion, telefono, ciudad)
VALUES (1, 'A todo gas', 'Calle Gran Vía 12', '910000001', 'Madrid'),
       (2, 'La Costa de Bolivia', 'Paseo Marítimo 45', '930000002', 'Barcelona'),
       (3, 'Aeropuerto Aterriza Como Puedas', 'Pista 2, Cuidado con los pájaros', '910000003', 'Madrid'),
       (4, 'Fondo de Bikini', 'Muelle de Poniente 3', '960000004', 'Valencia'),
       (5, 'Heliopuerto Esperanza', 'Zona Fórum s/n', '930000005', 'Barcelona');

-- 2. VEHÍCULOS (ACTIVO: Tienes la entidad Vehiculo)
INSERT IGNORE INTO vehiculos (id, matricula, plazas, precio_hora, tipo_vehiculo, sucursal_id)
VALUES (1, '1234ABC', 5, 45.50, 'TERRESTRE', 1),
       (2, '5678DEF', 9, 80.00, 'TERRESTRE', 1),
       (3, '9012GHI', 5, 55.00, 'TERRESTRE', 2),
       (4, '3456JKL', 2, 35.00, 'TERRESTRE', 2),
       (5, '7ª-VA-1-23', 2, 150.00, 'MARITIMO', 4),
       (6, '6ª-VA-2-23', 8, 950.00, 'MARITIMO', 4),
       (7, 'EC-ABC', 4, 1200.00, 'AEREO', 3),
       (8, 'EC-XYZ', 6, 2500.00, 'AEREO', 5);

/* BLOQUE COMENTADO:
   Las siguientes tablas darían error porque aún no tienes las clases Java (Entities)
   de Clientes ni Alquileres. Descoméntalo cuando las crees.
*/

-- 3. CLIENTES
 INSERT IGNORE INTO clientes (id, nombre, apellidos, email)
 VALUES (1, 'Esteban', 'Dido Buscado', 'esteban.dido@email.com'),
        (2, 'Elena', 'Nito Del Bosque', 'elena.nito@email.com'),
        (3, 'Estela', 'Gartija Veloz', 'estela.gartija@email.com'),
        (4, 'Armando', 'Bronca Segura', 'armando.lios@email.com'),
        (5, 'Inés', 'Plícable Ruta', 'ines.plicable@email.com'),
        (6, 'Pere', 'Gil Fresco', 'pere.gil@email.com');

-- Asegúrate de que los nombres de las columnas coincidan con lo que vimos en el DESCRIBE
INSERT IGNORE INTO alquileres (id, id_cliente, id_sucursal, precio_total, fecha_inicio, fecha_fin, estado)
VALUES (1, 1, 1, 136.50, '2024-03-15 10:00:00', '2024-03-18 10:00:00', 'COMPLETADO'),
       (2, 2, 2, 55.00, '2024-04-01 09:00:00', '2024-04-02 09:00:00', 'ACTIVO'),
       (3, 3, 4, 950.00, '2024-05-10 08:00:00', '2024-05-11 20:00:00', 'CANCELADO');

-- 5. TABLA INTERMEDIA
INSERT IGNORE INTO alquiler_vehiculos (alquiler_id, vehiculo_id)
VALUES (1, 1), (1, 2), (2, 3), (3, 6);