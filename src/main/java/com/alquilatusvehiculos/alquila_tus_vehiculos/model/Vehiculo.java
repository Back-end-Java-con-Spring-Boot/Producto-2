package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(length = 50, nullable = false)
    private String nombre;

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

    @Column(name = "imagen_url")
    private String imagenUrl;

    public void setId(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
