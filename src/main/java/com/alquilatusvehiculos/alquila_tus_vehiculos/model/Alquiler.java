package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

import jakarta.persistence.*;
import jakarta.websocket.ClientEndpoint;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    private BigDecimal precio_total;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado = EstadoAlquiler.ACTIVO;


}


