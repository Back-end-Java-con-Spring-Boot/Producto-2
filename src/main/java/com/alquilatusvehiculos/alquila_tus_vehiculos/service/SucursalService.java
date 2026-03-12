package com.alquilatusvehiculos.alquila_tus_vehiculos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Sucursal;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.SucursalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SucursalService {

    private final SucursalRepository sucursalRepository;

    // Obtener todas las sucursales para la tabla
    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    // Guardar o Actualizar
    public void guardar(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    // Borrar por ID
    public void borrar(Long id) {
        sucursalRepository.deleteById(id);
    }

    // Buscar una sola
    public Sucursal buscarPorId(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }
}