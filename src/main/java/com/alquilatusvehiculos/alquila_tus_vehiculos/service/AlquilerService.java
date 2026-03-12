public List<Alquiler> filtrarPorFecha(LocalDateTime fecha_inicio, LocalDateTime fecha_fin) {
    if (fecha_inicio.isAfter(fecha_fin)) {
        throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la de fin.");
    }
    // Change this line to match the new repository method name:
    return alquilerRepository.findByFechaInicioBetween(fecha_inicio, fecha_fin);
}