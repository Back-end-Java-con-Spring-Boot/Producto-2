package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    List<Alquiler> findByEstado(EstadoAlquiler estado);

    List<Alquiler> findByFecha_inicioBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Alquiler> findByClienteEmail(String email_cliente);
}
