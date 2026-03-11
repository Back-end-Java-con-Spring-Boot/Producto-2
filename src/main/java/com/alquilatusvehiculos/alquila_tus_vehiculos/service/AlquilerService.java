package com.alquilatusvehiculos.alquila_tus_vehiculos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.AlquilerRepository;

import jakarta.transaction.Transactional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<Alquiler> listarTodosAlquileres() {
        return alquilerRepository.findAll();
    }

    @Transactional
    public Alquiler crearAlquiler(Alquiler alquiler) {
        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public Alquiler actualizarAlquiler(Long id, Alquiler alquilerActualizado) {
        Alquiler alquilerExistente = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alquiler con ID: " + id));

        if (alquilerExistente.getEstado() == EstadoAlquiler.ACTIVO) {
            alquilerExistente.setFecha_fin(alquilerActualizado.getFecha_fin());
            alquilerExistente.setFecha_inicio(alquilerActualizado.getFecha_inicio());
            alquilerExistente.setVehiculos(alquilerActualizado.getVehiculos());
            return alquilerRepository.save(alquilerExistente);
        } else {
            throw new RuntimeException("No se puede actualizar una reserva finalizada o cancelada");
        }
    }

    @Transactional
    public Alquiler cancelarAlquiler(Long id) {
        Alquiler alquiler = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alquiler con ID: " + id));

        if (alquiler.getEstado() != EstadoAlquiler.ACTIVO) {
            throw new RuntimeException("Solo se pueden cancelar alquileres en estado ACTIVO");
        }

        alquiler.setEstado(EstadoAlquiler.CANCELADO);
        return alquilerRepository.save(alquiler);
    }

    // Filtros extras
    public List<Alquiler> filtrarPorEstado(EstadoAlquiler estadoSelecionado) {
        return alquilerRepository.findByEstado(estadoSelecionado);
    }

    public List<Alquiler> filtrarPorFecha(LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {

        if (fecha_inicio.isAfter(fecha_fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin.");
        }
        return alquilerRepository.findByFecha_inicioBetween(fecha_inicio, fecha_fin);
    }

    public List<Alquiler> filtrarPorCliente(String email_cliente) {
        List<Alquiler> alquileres = alquilerRepository.findByClienteEmail(email_cliente);
        return alquileres.isEmpty() ? new ArrayList<>() : alquileres;
    }
}