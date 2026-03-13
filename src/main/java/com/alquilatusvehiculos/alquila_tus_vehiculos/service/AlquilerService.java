package com.alquilatusvehiculos.alquila_tus_vehiculos.service;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.AlquilerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<Alquiler> listarTodosAlquileres() {
        return alquilerRepository.findAll();
    }

    @Transactional
    public BigDecimal calcularPrecio(Alquiler alquiler){

        long dias = Math.max(1,
                ChronoUnit.DAYS.between(alquiler.getFechaInicio(), alquiler.getFechaFin())
        );

        BigDecimal precioDia = alquiler.getVehiculos().stream()
                .map(v -> v.getPrecioHora().multiply(BigDecimal.valueOf(24)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return precioDia.multiply(BigDecimal.valueOf(dias));
    }


    @Transactional
    public Alquiler crearAlquiler(Alquiler alquiler) {
        if (alquiler.getFechaInicio().isAfter(alquiler.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la fecha de fin");
        }

        alquiler.setPrecioTotal(calcularPrecio(alquiler));
        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public Alquiler actualizarAlquiler(Long id, Alquiler alquilerActualizado) {

        Alquiler alquilerExistente = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alquiler con ID: " + id));

        if (alquilerExistente.getEstado() != EstadoAlquiler.ACTIVO) {
            throw new RuntimeException("No se puede actualizar una reserva finalizada o cancelada");
        }

        if (alquilerActualizado.getFechaInicio().isAfter(alquilerActualizado.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la fecha de fin");
        }

        alquilerExistente.setFechaInicio(alquilerActualizado.getFechaInicio());
        alquilerExistente.setFechaFin(alquilerActualizado.getFechaFin());
        alquilerExistente.setVehiculos(alquilerActualizado.getVehiculos());

        alquilerExistente.setPrecioTotal(calcularPrecio(alquilerExistente));

        return alquilerRepository.save(alquilerExistente);
    }

    @Transactional
    public Alquiler buscarPorId(Long id){
        return alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alquiler con ID: " + id));

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
        return alquilerRepository.findByFechaInicioBetween(fecha_inicio, fecha_fin);
    }

    public List<Alquiler> filtrarPorCliente(String nombre) {
        List<Alquiler> alquileres = alquilerRepository.findByClienteNombre(nombre);
        return alquileres.isEmpty() ? new ArrayList<>() : alquileres;
    }

}