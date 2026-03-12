package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Alquiler;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.AlquilerService;
// import com.alquilatusvehiculos.alquila_tus_vehiculos.service.ClienteService;
// import com.alquilatusvehiculos.alquila_tus_vehiculos.service.VehiculoService;
// import com.alquilatusvehiculos.alquila_tus_vehiculos.service.SucursalService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alquiler")
public class AlquilerController {

    private final AlquilerService alquilerService;
    // private final ClienteService clienteService;
    // private final VehiculoService vehiculoService;
    // private final SucursalService sucursalService;

    public AlquilerController(AlquilerService alquilerService
                              // , ClienteService clienteService
                              // , VehiculoService vehiculoService
                              // , SucursalService sucursalService
    ) {
        this.alquilerService = alquilerService;
        // this.clienteService = clienteService;
        // this.vehiculoService = vehiculoService;
        // this.sucursalService = sucursalService;
    }

    @GetMapping
    public String listarTodo(Model model){
        model.addAttribute("alquiler", alquilerService.listarTodosAlquileres());
        return "alquiler/lista";
    }

    @GetMapping("/crear")
    public String formularioAlquiler(Model model){
        model.addAttribute("alquiler", new Alquiler());

        // model.addAttribute("listaClientes", clienteService.listarTodos());
        // model.addAttribute("listaVehiculos", vehiculoService.listarTodos());
        // model.addAttribute("listaSucursales", sucursalService.listarTodas());

        return "alquiler/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alquiler alquiler){
        if (alquiler.getId() != null) {
            alquilerService.actualizarAlquiler(alquiler.getId(), alquiler);
        } else {
            alquilerService.crearAlquiler(alquiler);
        }
        return "redirect:/alquiler";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model){
        model.addAttribute("alquiler", alquilerService.buscarPorId(id));

        // model.addAttribute("listaClientes", clienteService.listarTodos());
        // model.addAttribute("listaVehiculos", vehiculoService.listarTodos());
        // model.addAttribute("listaSucursales", sucursalService.listarTodas());

        return "alquiler/formulario";
    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id){
        alquilerService.cancelarAlquiler(id);
        return "redirect:/alquiler";
    }
}