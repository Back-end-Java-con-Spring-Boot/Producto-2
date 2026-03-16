package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {

    List<Alquiler> findByEstado(EstadoAlquiler estado);

    List<Alquiler> findByFechaInicioBetween(LocalDateTime start, LocalDateTime end);

    List<Alquiler> findByClienteNombre(String nombre);

    //Lo usará vehiculos para no eliminar un vehículo que tenga un alquiler con estado ACTIVO
    boolean existsByVehiculosIdAndEstado(Long vehiculoId, EstadoAlquiler estado);

    //lo usará sucursal para no eliminar una sucursal que tenga un alquiler asociado

    boolean existsBySucursalId(Long sucursalId);

}