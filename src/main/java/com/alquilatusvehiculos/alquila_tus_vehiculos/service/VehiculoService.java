package com.alquilatusvehiculos.alquila_tus_vehiculos.service;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.AlquilerRepository;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.VehiculoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final AlquilerRepository alquilerRepository;

    @Transactional
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }


    @Transactional
    public Vehiculo findById(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo con ID " + id + " no encontrado"));
    }

    @Transactional
    public void save(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public void deleteById(Long id) {
        if(!vehiculoRepository.existsById(id)){
            throw new IllegalArgumentException("No se puede eliminar: Vehículo con ID " + id + " no existe");
        }
        //boolean estaEnUso = alquilerRepository.comprobarSiVehiculoEstaEnUso(id, EstadoAlquiler.ACTIVO);

       // if(estaEnUso){
       //     throw new IllegalStateException("No se puede eliminar: El vehículo está actualmente en un alquiler ACTIVO.");
        //}

        vehiculoRepository.deleteById(id);
    }
}
