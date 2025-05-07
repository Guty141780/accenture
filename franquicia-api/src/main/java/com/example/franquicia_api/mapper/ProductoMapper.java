package com.example.franquicia_api.mapper;

import com.example.franquicia_api.dto.ProductoDTO;
import com.example.franquicia_api.model.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoDTO dto) {
        Producto p = new Producto();
        p.setId(dto.getId());
        p.setNombre(dto.getNombre());
        p.setStock(dto.getStock());
        return p;
    }

    public static ProductoDTO toDTO(Producto p) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setStock(p.getStock());
        return dto;
    }
}