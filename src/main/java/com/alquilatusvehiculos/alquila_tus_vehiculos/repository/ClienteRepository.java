package com.alquilatusvehiculos.alquila_tus_vehiculos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Buscar por email (útil para validar duplicados)
    Optional<Cliente> findByEmail(String email);

    // Buscar por apellidos (útil para el buscador)
    java.util.List<Cliente> findByApellidosContainingIgnoreCase(String apellidos);
}