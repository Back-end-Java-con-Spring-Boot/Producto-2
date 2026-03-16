package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;


import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.EstadoAlquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Vehiculo;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.AlquilerService;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.ClienteService;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.VehiculoService;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.SucursalService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
@RequestMapping("/alquiler")
public class AlquilerController {

    private final AlquilerService alquilerService;
    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;
    private final SucursalService sucursalService;

    public AlquilerController(AlquilerService alquilerService , SucursalService sucursalService
                              , ClienteService clienteService
                              , VehiculoService vehiculoService

    ) {
        this.alquilerService = alquilerService;
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public String listarTodo(Model model){
        model.addAttribute("alquiler", alquilerService.listarTodosAlquileres());
        return "alquiler/lista";
    }

    @GetMapping("/crear")
    public String formularioAlquiler(@ModelAttribute Alquiler alquiler, @RequestParam(required = false) Long vehiculoId, Model model){

        if (alquiler.getVehiculos() == null) {
            alquiler.setVehiculos(new ArrayList<>());
        }

        //Codigo añadido para mostrar el vehículo en el formulario al hacer click en reservas Alquilar Vehiculo y en el formulario del inicio
        if(vehiculoId != null){
            try{
                Vehiculo vehiculoSeleccionado = vehiculoService.findById(vehiculoId);
                alquiler.getVehiculos().add(vehiculoSeleccionado);
                if (alquiler.getSucursal() == null || alquiler.getSucursal().getId() == null) {
                    alquiler.setSucursal(vehiculoSeleccionado.getSucursal());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Vehículo no encontrado: " + e.getMessage());
            }
        }

        model.addAttribute("alquiler", alquiler);

        model.addAttribute("listaClientes", clienteService.findAll());
        model.addAttribute("listaVehiculos", vehiculoService.findAll());
        model.addAttribute("listaSucursales", sucursalService.obtenerTodas());

        model.addAttribute("fechaMinima", LocalDateTime.now());
        return "alquiler/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alquiler alquiler, Model model) {
        try {
            alquiler.setFechaInicio(
                    LocalDateTime.of(alquiler.getFechaInicioDate(), alquiler.getFechaInicioTime())
            );
            alquiler.setFechaFin(
                    LocalDateTime.of(alquiler.getFechaFinDate(), alquiler.getFechaFinTime())
            );

            if (alquiler.getId() != null) {
                alquilerService.actualizarAlquiler(alquiler.getId(), alquiler);
            } else {
                alquilerService.crearAlquiler(alquiler);
            }

            return "redirect:/alquiler";

        } catch (RuntimeException e) {

            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("listaClientes", clienteService.findAll());
            model.addAttribute("listaVehiculos", vehiculoService.findAll());
            model.addAttribute("listaSucursales", sucursalService.obtenerTodas());

            return "alquiler/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            Alquiler alquiler = alquilerService.buscarPorId(id);

            if (alquiler.getEstado() != EstadoAlquiler.ACTIVO) {
                model.addAttribute("errorMessage", "No se puede editar una reserva finalizada o cancelada");

                model.addAttribute("alquiler", alquilerService.listarTodosAlquileres());

                return "alquiler/lista";
            }

            model.addAttribute("alquiler", alquiler);
            model.addAttribute("listaClientes", clienteService.findAll());
            model.addAttribute("listaVehiculos", vehiculoService.findAll());
            model.addAttribute("listaSucursales", sucursalService.obtenerTodas());

            return "alquiler/formulario";

        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("alquiler", alquilerService.listarTodosAlquileres());

            return "alquiler/lista";
        }
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, Model model){
        try{
        alquilerService.cancelarAlquiler(id);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());

            model.addAttribute("alquiler", alquilerService.listarTodosAlquileres());
            return "alquiler/lista";
        }
        return "redirect:/alquiler";
    }
}