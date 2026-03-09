package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}
