package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.SucursalRepository;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;
    private final SucursalRepository sucursalRepository;

    /* CREATE */
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model){
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("listaSucursales", sucursalRepository.findAll());
        return "vehiculos/formulario";
    }
    /**
     * Este método recibirá los datos del formulario cuando el usuario pulse guardar.
     */
    @PostMapping("/nuevo")
    public String guardarVehiculo(Vehiculo nuevoVehiculo){
        vehiculoRepository.save(nuevoVehiculo);
        return "redirect:/vehiculos";
    }

    /* READ */
    @GetMapping
    public String listarVehiculos(Model model){
        var vehiculos = vehiculoRepository.findAll();
        model.addAttribute("vehiculos", vehiculos);
        //nombre de la vista de thymeleaf
        return "vehiculos/admin";
    }

    /* UPDATE */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model){
        Vehiculo vehiculo = vehiculoRepository.findById(id).get();
        model.addAttribute("vehiculo",vehiculo);
        model.addAttribute("listaSucursales", sucursalRepository.findAll());
        return "vehiculos/formulario";
    }
    /**
     * Este método recibirá los datos del formulario cuando el usuario pulse guardar/editar.
     */
    @PostMapping("/editar/{id}")
    public String editarVehiculo(@PathVariable Long id, Vehiculo vehiculo){
        vehiculo.setId(id);
        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos";
    }

    /* DELETE */
    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id){
        vehiculoRepository.deleteById(id);
        return "redirect:/vehiculos";
    }
}
