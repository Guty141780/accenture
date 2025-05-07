package com.example.franquicia_api.controller;

import com.example.franquicia_api.dto.ActualizarNombreDTO;
import com.example.franquicia_api.dto.ActualizarStockDTO;
import com.example.franquicia_api.dto.FranquiciaDTO;
import com.example.franquicia_api.dto.ProductoDTO;
import com.example.franquicia_api.dto.ProductoMaxStockDTO;
import com.example.franquicia_api.dto.RespuestasDTO;
import com.example.franquicia_api.dto.SucursalDTO;
import com.example.franquicia_api.service.FranquiciaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/franquicias")
@RequiredArgsConstructor
@Slf4j
public class FranquiciaController {

    private final FranquiciaService franquiciaService;

    @PostMapping
    public ResponseEntity<RespuestasDTO<FranquiciaDTO>> crearFranquicia(@Valid @RequestBody FranquiciaDTO dto) {
        log.info("[crearFranquicia] Request: {}", dto);
        FranquiciaDTO respuesta = franquiciaService.crearFranquicia(dto);
        String mensaje = String.format("Franquicia '%s' creada correctamente.", respuesta.getNombre());
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, respuesta));
    }

    @PostMapping("/{id}/sucursales")
    public ResponseEntity<RespuestasDTO<SucursalDTO>> agregarSucursal(
            @PathVariable String id,
            @Valid @RequestBody SucursalDTO sucursalDTO) {
        log.info("[agregarSucursal] Franquicia ID: {}, Request: {}", id, sucursalDTO);
        SucursalDTO respuesta = franquiciaService.agregarSucursal(id, sucursalDTO);
        String mensaje = String.format("Sucursal '%s' agregada correctamente a la franquicia '%s'.", respuesta.getNombre(), id);
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, respuesta));
    }

    @PostMapping("/{idFranquicia}/sucursales/{idSucursal}/productos")
    public ResponseEntity<RespuestasDTO<ProductoDTO>> agregarProducto(
            @PathVariable String idFranquicia,
            @PathVariable String idSucursal,
            @Valid @RequestBody ProductoDTO productoDTO) {
        log.info("[agregarProducto] Request: {}", productoDTO);
        ProductoDTO respuesta = franquiciaService.agregarProducto(idFranquicia, idSucursal, productoDTO);
        String mensaje = String.format("Producto '%s' agregado correctamente a la sucursal '%s'.", respuesta.getNombre(), idSucursal);
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, respuesta));
    }

    @PutMapping("/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}")
    public ResponseEntity<RespuestasDTO<ProductoDTO>> actualizarStock(
            @PathVariable String idFranquicia,
            @PathVariable String idSucursal,
            @PathVariable String idProducto,
            @Valid @RequestBody ActualizarStockDTO dto) {
        log.info("📥 [PUT actualizarStock] Request: {}", dto);
        ProductoDTO actualizado = franquiciaService.actualizarStock(idFranquicia, idSucursal, idProducto, dto.getStock());
        String mensaje = String.format("Stock del producto con ID '%s' actualizado correctamente.", actualizado.getId());
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, actualizado));
    }

    @DeleteMapping("/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}")
    public ResponseEntity<RespuestasDTO<ProductoDTO>> eliminarProducto(
            @PathVariable String idFranquicia,
            @PathVariable String idSucursal,
            @PathVariable String idProducto) {
        log.info("[DELETE producto] Franquicia: {}, Sucursal: {}, Producto: {}", idFranquicia, idSucursal, idProducto);
        ProductoDTO eliminado = franquiciaService.eliminarProducto(idFranquicia, idSucursal, idProducto);
        String mensaje = String.format("Producto con ID '%s' eliminado correctamente.", eliminado.getId());
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, eliminado));
    }

    @GetMapping
    public ResponseEntity<List<FranquiciaDTO>> obtenerTodas() {
        log.info("[GET /api/franquicias] Consultando franquicias");
        return ResponseEntity.ok(franquiciaService.obtenerTodas());
    }

    @GetMapping("/{id}/productos/max-stock")
    public ResponseEntity<List<ProductoMaxStockDTO>> obtenerProductosConMasStock(
            @PathVariable String id) {
        log.info("[GET productos/max-stock] Franquicia ID: {}", id);
        return ResponseEntity.ok(franquiciaService.obtenerProductosConMasStock(id));
    }
    
    @PatchMapping("/{id}/nombre")
    public ResponseEntity<RespuestasDTO<FranquiciaDTO>> actualizarNombreFranquicia(
            @PathVariable String id,
            @Valid @RequestBody ActualizarNombreDTO dto) {

        FranquiciaDTO actualizada = franquiciaService.actualizarNombreFranquicia(id, dto.getNuevoNombre());
        String mensaje = String.format("Nombre de la franquicia con ID '%s' actualizado correctamente.", id);
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, actualizada));
    }

    @PatchMapping("/{idFranquicia}/sucursales/{idSucursal}/nombre")
    public ResponseEntity<RespuestasDTO<SucursalDTO>> actualizarNombreSucursal(
            @PathVariable String idFranquicia,
            @PathVariable String idSucursal,
            @Valid @RequestBody ActualizarNombreDTO dto) {

        SucursalDTO actualizada = franquiciaService.actualizarNombreSucursal(idFranquicia, idSucursal, dto.getNuevoNombre());
        String mensaje = String.format("Nombre de la sucursal con ID '%s' actualizado correctamente.", idSucursal);
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, actualizada));
    }

    @PatchMapping("/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/nombre")
    public ResponseEntity<RespuestasDTO<ProductoDTO>> actualizarNombreProducto(
            @PathVariable String idFranquicia,
            @PathVariable String idSucursal,
            @PathVariable String idProducto,
            @Valid @RequestBody ActualizarNombreDTO dto) {

        ProductoDTO actualizado = franquiciaService.actualizarNombreProducto(idFranquicia, idSucursal, idProducto, dto.getNuevoNombre());
        String mensaje = String.format("Nombre del producto con ID '%s' actualizado correctamente.", idProducto);
        return ResponseEntity.ok(new RespuestasDTO<>(mensaje, actualizado));
    }
}