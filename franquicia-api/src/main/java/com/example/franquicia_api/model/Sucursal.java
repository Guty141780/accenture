package com.example.franquicia_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sucursal {
    private String id;
    private String nombre;
    private List<Producto> productos;
}