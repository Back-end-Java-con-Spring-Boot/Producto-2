package com.alquilatusvehiculos.alquila_tus_vehiculos.service;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.AlquilerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

        LocalDateTime inicio = alquiler.getFechaInicio();
        LocalDateTime fin = alquiler.getFechaFin();

        long dias = ChronoUnit.DAYS.between(inicio.toLocalDate(), fin.toLocalDate());

        if (fin.toLocalTime().isAfter(inicio.toLocalTime())) {
            dias++;
        }

        dias = Math.max(1, dias);

        BigDecimal precioTotalPorDia = alquiler.getVehiculos().stream()
                .map(Vehiculo::getPrecioDia)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return precioTotalPorDia.multiply(BigDecimal.valueOf(dias));
    }


    @Transactional
    public Alquiler crearAlquiler(Alquiler alquiler) {
        LocalDate hoy = LocalDate.now();

        if (alquiler.getFechaInicio().toLocalDate().isBefore(hoy)) {
            throw new RuntimeException("La fecha de inicio no puede ser pasada");
        }

        if (alquiler.getFechaInicio().isAfter(alquiler.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la fecha de fin");
        }

        if (alquiler.getVehiculos() == null || alquiler.getVehiculos().isEmpty()) {
            throw new RuntimeException("Debes seleccionar al menos un vehículo");
        }

        alquiler.setPrecioTotal(calcularPrecio(alquiler));
        return alquilerRepository.save(alquiler);
    }
    @Transactional
    public Alquiler actualizarAlquiler(Long id, Alquiler alquilerActualizado) {

        LocalDate hoy = LocalDate.now();

        Alquiler alquilerExistente = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alquiler con ID: " + id));

        if (alquilerExistente.getEstado() != EstadoAlquiler.ACTIVO) {
            throw new RuntimeException("No se puede actualizar una reserva finalizada o cancelada");
        }
        if (alquilerActualizado.getFechaInicio().toLocalDate().isBefore(hoy)) {
            throw new RuntimeException("La fecha de inicio no puede ser pasada");
        }

        if (alquilerActualizado.getFechaInicio().isAfter(alquilerActualizado.getFechaFin())) {
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la fecha de fin");
        }
        if (alquilerActualizado.getVehiculos() == null || alquilerActualizado.getVehiculos().isEmpty()) {
            throw new RuntimeException("Debes seleccionar al menos un vehículo");
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