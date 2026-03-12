package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "alquileres")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @ManyToMany
    @JoinTable(
            name = "alquiler_vehiculos",
            joinColumns = @JoinColumn(name = "alquiler_id"),
            inverseJoinColumns = @JoinColumn(name = "vehiculo_id")
    )
    private List<Vehiculo> vehiculos;

    private BigDecimal precioTotal;    // was precio_total
    private LocalDateTime fechaInicio; // was fecha_inicio
    private LocalDateTime fechaFin;    // was fecha_fin

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado = EstadoAlquiler.ACTIVO;

}
