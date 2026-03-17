package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.SucursalService;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.VehiculoService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Controller
@RequestMapping("/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final SucursalService sucursalService;

    /* CREATE */
    @GetMapping("/nuevo")
    public String mostrarFormularioCrear(Model model){
        model.addAttribute("vehiculo", new Vehiculo());
        model.addAttribute("listaSucursales", sucursalService.obtenerTodas());
        return "vehiculos/formulario";
    }
    /**
     * Este método recibirá los datos del formulario cuando el usuario pulse guardar.
     */
    @PostMapping("/nuevo")
    public String guardarVehiculo(Vehiculo nuevoVehiculo, RedirectAttributes redirectAttrs){
        vehiculoService.save(nuevoVehiculo);
        redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo creado correctamente.");

        return "redirect:/vehiculos";
    }

    /* READ */
    @GetMapping
    public String listarVehiculos(Model model){
        var vehiculos = vehiculoService.findAll();
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos/admin";
    }

    /* UPDATE */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs){
        try{
            Vehiculo vehiculo = vehiculoService.findById(id);
            model.addAttribute("vehiculo",vehiculo);
            model.addAttribute("listaSucursales", sucursalService.obtenerTodas());
            return "vehiculos/formulario";
        } catch (IllegalArgumentException e){
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
            return "redirect:/vehiculos";
        }

    }
    /**
     * Este método recibirá los datos del formulario cuando el usuario pulse guardar/editar.
     */
    @PostMapping("/editar/{id}")
    public String editarVehiculo(@PathVariable Long id, Vehiculo vehiculo, RedirectAttributes redirectAttrs){
        vehiculo.setId(id);
        vehiculoService.save(vehiculo);
        redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo editado correctamente.");
        return "redirect:/vehiculos";
    }

    /* DELETE */
    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id, RedirectAttributes redirectAttrs){
        try {
            vehiculoService.deleteById(id);
            redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo eliminado correctamente.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "No se puede eliminar: El vehículo está asociado a un historial de alquileres.");
        }
        return "redirect:/vehiculos";
    }

    @GetMapping("/vehiculos/sucursal/{id}")
    @ResponseBody
    public List<Vehiculo> vehiculosPorSucursalJson(@PathVariable Long id){
        return vehiculoService.findBySucursal(id);
    }



}