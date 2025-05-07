package com.example.franquicia_api.service;

import java.util.List;

import com.example.franquicia_api.dto.FranquiciaDTO;
import com.example.franquicia_api.dto.ProductoDTO;
import com.example.franquicia_api.dto.ProductoMaxStockDTO;
import com.example.franquicia_api.dto.SucursalDTO;

public interface FranquiciaService {
    FranquiciaDTO crearFranquicia(FranquiciaDTO dto);
    SucursalDTO agregarSucursal(String idFranquicia, SucursalDTO sucursalDTO);
    ProductoDTO agregarProducto(String idFranquicia, String idSucursal, ProductoDTO productoDTO);
    List<FranquiciaDTO> obtenerTodas();
    ProductoDTO eliminarProducto(String idFranquicia, String idSucursal, String idProducto);
    ProductoDTO actualizarStock(String idFranquicia, String idSucursal, String idProducto, int nuevoStock);
    List<ProductoMaxStockDTO> obtenerProductosConMasStock(String idFranquicia);
    FranquiciaDTO actualizarNombreFranquicia(String id, String nuevoNombre);
    SucursalDTO actualizarNombreSucursal(String idFranquicia, String idSucursal, String nuevoNombre);
    ProductoDTO actualizarNombreProducto(String idFranquicia, String idSucursal, String idProducto, String nuevoNombre);
}