package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "alquileres")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private  Sucursal sucursal;

    private BigDecimal precio_total;
    private LocalDateTime fecha_inicio;
    private  LocalDateTime fecha_fin;

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado = EstadoAlquiler.ACTIVO;



}
