package com.alquilatusvehiculos.alquila_tus_vehiculos.model;

/**
 * Define los tipos de vehículos existentes.
 * El display name muestra el nombre user-friendly para la UI
 */
public enum TipoVehiculo {

    MARITIMO("Marítimo"),
    AEREO("Aéreo"),
    TERRESTRE("Terrestre");

    private final String displayName;

    /**
     * constructor para el TipoVehiculo enum
     * @param displayName el nombre formateado
     */
    TipoVehiculo(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
