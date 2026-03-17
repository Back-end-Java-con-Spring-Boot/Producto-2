package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}