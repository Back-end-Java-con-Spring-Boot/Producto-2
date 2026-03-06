package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, unique = true, nullable = false)
    private String matricula;

    @Column(nullable = false)
    private int plazas;

    @Column(name = "precio_hora",nullable = false, precision = 10, scale = 2)
    private BigDecimal precioHora;

    @Column(name = "tipo_vehiculo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;
}
