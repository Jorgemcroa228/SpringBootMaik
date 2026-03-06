package com.jorge.prueba2.dto;

public class BuyRequestDTO {
    
    private int cantidad;
    
    public BuyRequestDTO() {
    }
    
    public BuyRequestDTO(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
