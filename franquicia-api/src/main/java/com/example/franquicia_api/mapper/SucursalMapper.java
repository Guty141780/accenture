package com.example.franquicia_api.mapper;

import com.example.franquicia_api.dto.SucursalDTO;
import com.example.franquicia_api.model.Sucursal;

import java.util.ArrayList;

public class SucursalMapper {

    public static Sucursal toEntity(SucursalDTO dto) {
        Sucursal s = new Sucursal();
        s.setId(dto.getId());
        s.setNombre(dto.getNombre());
        s.setProductos(new ArrayList<>());
        return s;
    }

    public static SucursalDTO toDTO(Sucursal s) {
        SucursalDTO dto = new SucursalDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setProductos(new ArrayList<>());
        return dto;
    }
}