package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
}