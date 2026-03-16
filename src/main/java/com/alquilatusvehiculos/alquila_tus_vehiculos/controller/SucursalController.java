package com.alquilatusvehiculos.alquila_tus_vehiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Sucursal;
import com.alquilatusvehiculos.alquila_tus_vehiculos.service.SucursalService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public String mostrarSucursales(Model model) {
        model.addAttribute("listaSucursal", sucursalService.obtenerTodas());
        model.addAttribute("nuevaSucursal", new Sucursal());
        return "sucursales/admin";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Sucursal sucursal) {
        sucursalService.guardar(sucursal);
        return "redirect:/sucursales";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            sucursalService.borrar(id);
            redirectAttrs.addFlashAttribute("mensajeExito", "Sucursal eliminada correctamente.");
        } catch (IllegalStateException | IllegalArgumentException e) {
                redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
            } catch (Exception e) {
                redirectAttrs.addFlashAttribute("mensajeError", "No se puede eliminar: La sucursal está asociada a un historial de alquileres.");
            }

        return "redirect:/sucursales";
    }
}