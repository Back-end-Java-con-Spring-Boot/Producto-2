package com.alquilatusvehiculos.alquila_tus_vehiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.alquilatusvehiculos.alquila_tus_vehiculos.model.Cliente;
import com.alquilatusvehiculos.alquila_tus_vehiculos.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // ── READ ───────────────────────────────────────────────────────
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Alias para compatibilidad con AlquilerController del equipo
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // ── CREATE / UPDATE ────────────────────────────────────────────
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // ── DELETE ─────────────────────────────────────────────────────
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    // ── VALIDACIÓN email duplicado ─────────────────────────────────
    public boolean emailYaExiste(String email, Long idActual) {
        Optional<Cliente> existente = clienteRepository.findByEmail(email);
        return existente.isPresent() && !existente.get().getId().equals(idActual);
    }
}