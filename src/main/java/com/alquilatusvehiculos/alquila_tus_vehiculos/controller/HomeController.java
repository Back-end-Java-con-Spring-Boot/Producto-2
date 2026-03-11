package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;

import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.SucursalRepository;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final VehiculoRepository vehiculoRepository;
    private final SucursalRepository sucursalRepository;

    @GetMapping("/")
    public String index() {
        // Busca en templates/index.html
        return "index";
    }

    @GetMapping("/reservar")
    public String listarCatalogo(Model model){
        model.addAttribute("listaSucursales", sucursalRepository.findAll());
        model.addAttribute("listaVehiculos", vehiculoRepository.findAll());
        return "reservar";
    }

}