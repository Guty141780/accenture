package com.example.franquicia_api.mapper;

import com.example.franquicia_api.dto.FranquiciaDTO;
import com.example.franquicia_api.dto.ProductoDTO;
import com.example.franquicia_api.dto.SucursalDTO;
import com.example.franquicia_api.model.Franquicia;
import com.example.franquicia_api.model.Producto;
import com.example.franquicia_api.model.Sucursal;

import java.util.List;
import java.util.stream.Collectors;

public class FranquiciaMapper {

    public static Franquicia toEntity(FranquiciaDTO dto) {
        Franquicia franquicia = new Franquicia();
        franquicia.setId(dto.getId());
        franquicia.setNombre(dto.getNombre());
        if (dto.getSucursales() != null) {
            franquicia.setSucursales(dto.getSucursales().stream().map(sucursalDto -> {
                Sucursal sucursal = new Sucursal();
                sucursal.setId(sucursalDto.getId());
                sucursal.setNombre(sucursalDto.getNombre());
                if (sucursalDto.getProductos() != null) {
                    sucursal.setProductos(sucursalDto.getProductos().stream().map(productoDto -> {
                        Producto producto = new Producto();
                        producto.setId(productoDto.getId());
                        producto.setNombre(productoDto.getNombre());
                        producto.setStock(productoDto.getStock());
                        return producto;
                    }).collect(Collectors.toList()));
                }
                return sucursal;
            }).collect(Collectors.toList()));
        }
        return franquicia;
    }

    public static FranquiciaDTO toDTO(Franquicia entity) {
        FranquiciaDTO dto = new FranquiciaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        if (entity.getSucursales() != null) {
            dto.setSucursales(entity.getSucursales().stream().map(sucursal -> {
                SucursalDTO sucursalDto = new SucursalDTO();
                sucursalDto.setId(sucursal.getId());
                sucursalDto.setNombre(sucursal.getNombre());
                if (sucursal.getProductos() != null) {
                    sucursalDto.setProductos(sucursal.getProductos().stream().map(producto -> {
                        ProductoDTO productoDto = new ProductoDTO();
                        productoDto.setId(producto.getId());
                        productoDto.setNombre(producto.getNombre());
                        productoDto.setStock(producto.getStock());
                        return productoDto;
                    }).collect(Collectors.toList()));
                }
                return sucursalDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}