package com.example.franquicia_api.dto;

import lombok.Data;

@Data
public class ProductoMaxStockDTO {
    private String id;
    private String nombre;
    private int stock;
    private String sucursalId;
    private String sucursalNombre;
}