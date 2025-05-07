package com.example.franquicia_api.service.impl;

import com.example.franquicia_api.dto.FranquiciaDTO;
import com.example.franquicia_api.dto.ProductoDTO;
import com.example.franquicia_api.dto.ProductoMaxStockDTO;
import com.example.franquicia_api.dto.SucursalDTO;
import com.example.franquicia_api.exception.EntidadNoEncontradaException;
import com.example.franquicia_api.mapper.FranquiciaMapper;
import com.example.franquicia_api.mapper.ProductoMapper;
import com.example.franquicia_api.mapper.SucursalMapper;
import com.example.franquicia_api.model.Franquicia;
import com.example.franquicia_api.model.Producto;
import com.example.franquicia_api.model.Sucursal;
import com.example.franquicia_api.repository.FranquiciaRepository;
import com.example.franquicia_api.service.FranquiciaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FranquiciaServiceImpl implements FranquiciaService {

    private final FranquiciaRepository franquiciaRepository;

    @Override
    public FranquiciaDTO crearFranquicia(FranquiciaDTO dto) {
        log.info("[crearFranquicia] Validando existencia del ID: {}", dto.getId());

        boolean yaExiste = franquiciaRepository.existsById(dto.getId());
        if (yaExiste) {
            log.error("[crearFranquicia] Ya existe una franquicia con ID: {}", dto.getId());
            throw new IllegalArgumentException("Ya existe una franquicia con el ID: " + dto.getId());
        }

        Franquicia franquicia = FranquiciaMapper.toEntity(dto);
        Franquicia guardado = franquiciaRepository.save(franquicia);
        FranquiciaDTO respuesta = FranquiciaMapper.toDTO(guardado);

        log.info("[crearFranquicia] Franquicia guardada: {}", respuesta);
        return respuesta;
    }

    @Override
    public SucursalDTO agregarSucursal(String idFranquicia, SucursalDTO sucursalDTO) {
        log.info("[agregarSucursal] Buscando franquicia ID: {}", idFranquicia);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> {
                    log.error("[agregarSucursal] Franquicia no encontrada: {}", idFranquicia);
                    return new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia);
                });

        List<Sucursal> sucursales = franquicia.getSucursales();
        if (sucursales == null) sucursales = new ArrayList<>();

        boolean existe = sucursales.stream()
                .anyMatch(s -> s.getId().equalsIgnoreCase(sucursalDTO.getId()));

        if (existe) {
            log.error("[agregarSucursal] Ya existe una sucursal con ID: {}", sucursalDTO.getId());
            throw new IllegalArgumentException("Ya existe una sucursal con el ID: " + sucursalDTO.getId());
        }

        Sucursal nuevaSucursal = SucursalMapper.toEntity(sucursalDTO);

        sucursales.add(nuevaSucursal);
        franquicia.setSucursales(sucursales);
        franquiciaRepository.save(franquicia);

        SucursalDTO respuesta = SucursalMapper.toDTO(nuevaSucursal);

        log.info("[agregarSucursal] Sucursal agregada: {}", respuesta);
        return respuesta;
    }

    @Override
    public ProductoDTO agregarProducto(String idFranquicia, String idSucursal, ProductoDTO productoDTO) {
        log.info("[agregarProducto] Buscar franquicia ID: {}", idFranquicia);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> {
                    log.error("[agregarProducto] Franquicia no encontrada: {}", idFranquicia);
                    return new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia);
                });

        List<Sucursal> sucursales = franquicia.getSucursales();
        if (sucursales == null || sucursales.isEmpty()) {
            log.error("[agregarProducto] La franquicia no tiene sucursales");
            throw new EntidadNoEncontradaException("La franquicia no tiene sucursales");
        }

        Sucursal sucursal = sucursales.stream()
                .filter(s -> s.getId().equalsIgnoreCase(idSucursal))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[agregarProducto] Sucursal no encontrada: {}", idSucursal);
                    return new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + idSucursal);
                });

        List<Producto> productos = sucursal.getProductos();
        if (productos == null) productos = new ArrayList<>();

        boolean existe = productos.stream()
                .anyMatch(p -> p.getId().equalsIgnoreCase(productoDTO.getId()));

        if (existe) {
            log.error("[agregarProducto] Ya existe un producto con ID: {}", productoDTO.getId());
            throw new IllegalArgumentException("Ya existe un producto con el ID: " + productoDTO.getId());
        }

        Producto nuevoProducto = ProductoMapper.toEntity(productoDTO);

        productos.add(nuevoProducto);
        sucursal.setProductos(productos);
        franquiciaRepository.save(franquicia);

        ProductoDTO respuesta = ProductoMapper.toDTO(nuevoProducto);
        log.info("[agregarProducto] Producto agregado: {}", respuesta);
        return respuesta;
    }
    
    @Override
    public List<FranquiciaDTO> obtenerTodas() {
        log.info("[obtenerTodas] Consultando todas las franquicias");

        List<Franquicia> franquicias = franquiciaRepository.findAll();

        List<FranquiciaDTO> resultado = franquicias.stream()
                .map(FranquiciaMapper::toDTO)
                .toList();

        log.info("[obtenerTodas] Total encontradas: {}", resultado.size());
        return resultado;
    }
    
    @Override
    public ProductoDTO eliminarProducto(String idFranquicia, String idSucursal, String idProducto) {
        log.info("[eliminarProducto] Buscar franquicia ID: {}", idFranquicia);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> {
                    log.error("[eliminarProducto] Franquicia no encontrada: {}", idFranquicia);
                    return new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia);
                });

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equalsIgnoreCase(idSucursal))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[eliminarProducto] Sucursal no encontrada: {}", idSucursal);
                    return new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + idSucursal);
                });

        List<Producto> productos = sucursal.getProductos();
        if (productos == null || productos.isEmpty()) {
            log.error("[eliminarProducto] La sucursal no tiene productos");
            throw new EntidadNoEncontradaException("La sucursal no tiene productos");
        }

        Producto productoEliminado = productos.stream()
                .filter(p -> p.getId().equalsIgnoreCase(idProducto))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[eliminarProducto] Producto no encontrado: {}", idProducto);
                    return new EntidadNoEncontradaException("Producto no encontrado con ID: " + idProducto);
                });

        productos.removeIf(p -> p.getId().equalsIgnoreCase(idProducto));
        sucursal.setProductos(productos);
        franquiciaRepository.save(franquicia);

        ProductoDTO dto = ProductoMapper.toDTO(productoEliminado);
        log.info("[eliminarProducto] Producto eliminado: {}", dto);
        return dto;
    }
    
    @Override
    public ProductoDTO actualizarStock(String idFranquicia, String idSucursal, String idProducto, int nuevoStock) {
        log.info("[actualizarStock] Franquicia: {}, Sucursal: {}, Producto: {}, Nuevo stock: {}",
                idFranquicia, idSucursal, idProducto, nuevoStock);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> {
                    log.error("[actualizarStock] Franquicia no encontrada: {}", idFranquicia);
                    return new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia);
                });

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equalsIgnoreCase(idSucursal))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[actualizarStock] Sucursal no encontrada: {}", idSucursal);
                    return new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + idSucursal);
                });

        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getId().equalsIgnoreCase(idProducto))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("[actualizarStock] Producto no encontrado: {}", idProducto);
                    return new EntidadNoEncontradaException("Producto no encontrado con ID: " + idProducto);
                });

        producto.setStock(nuevoStock);
        franquiciaRepository.save(franquicia);

        ProductoDTO respuesta = ProductoMapper.toDTO(producto);
        log.info("[actualizarStock] Producto actualizado: {}", respuesta);
        return respuesta;
    }
    
    @Override
    public List<ProductoMaxStockDTO> obtenerProductosConMasStock(String idFranquicia) {
        log.info("[obtenerProductosConMasStock] Buscar franquicia ID: {}", idFranquicia);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> {
                    log.error("[obtenerProductosConMasStock] Franquicia no encontrada: {}", idFranquicia);
                    return new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia);
                });

        List<ProductoMaxStockDTO> resultado = new ArrayList<>();

        for (Sucursal sucursal : franquicia.getSucursales()) {
            if (sucursal.getProductos() == null || sucursal.getProductos().isEmpty()) {
                continue;
            }

            Producto max = sucursal.getProductos().stream()
                    .max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock()))
                    .orElse(null);

            if (max != null) {
                ProductoMaxStockDTO dto = new ProductoMaxStockDTO();
                dto.setId(max.getId());
                dto.setNombre(max.getNombre());
                dto.setStock(max.getStock());
                dto.setSucursalId(sucursal.getId());
                dto.setSucursalNombre(sucursal.getNombre());
                resultado.add(dto);
            }
        }

        log.info("[obtenerProductosConMasStock] Total productos con mayor stock: {}", resultado.size());
        return resultado;
    }
    
    @Override
    public FranquiciaDTO actualizarNombreFranquicia(String id, String nuevoNombre) {
        log.info("[actualizarNombreFranquicia] ID: {}, nuevoNombre: {}", id, nuevoNombre);

        Franquicia franquicia = franquiciaRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + id));

        franquicia.setNombre(nuevoNombre);
        Franquicia actualizada = franquiciaRepository.save(franquicia);

        FranquiciaDTO respuesta = FranquiciaMapper.toDTO(actualizada);
        log.info("[actualizarNombreFranquicia] Actualizado: {}", respuesta);
        return respuesta;
    }
    
    @Override
    public SucursalDTO actualizarNombreSucursal(String idFranquicia, String idSucursal, String nuevoNombre) {
        log.info("[actualizarNombreSucursal] Franquicia: {}, Sucursal: {}, nuevoNombre: {}", idFranquicia, idSucursal, nuevoNombre);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia));

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equalsIgnoreCase(idSucursal))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + idSucursal));

        sucursal.setNombre(nuevoNombre);
        franquiciaRepository.save(franquicia);

        SucursalDTO respuesta = SucursalMapper.toDTO(sucursal);
        log.info("[actualizarNombreSucursal] Actualizada: {}", respuesta);
        return respuesta;
    }
    
    @Override
    public ProductoDTO actualizarNombreProducto(String idFranquicia, String idSucursal, String idProducto, String nuevoNombre) {
        log.info("[actualizarNombreProducto] Franquicia: {}, Sucursal: {}, Producto: {}, nuevoNombre: {}",
                idFranquicia, idSucursal, idProducto, nuevoNombre);

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia)
                .orElseThrow(() -> new EntidadNoEncontradaException("Franquicia no encontrada con ID: " + idFranquicia));

        Sucursal sucursal = franquicia.getSucursales().stream()
                .filter(s -> s.getId().equalsIgnoreCase(idSucursal))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Sucursal no encontrada con ID: " + idSucursal));

        Producto producto = sucursal.getProductos().stream()
                .filter(p -> p.getId().equalsIgnoreCase(idProducto))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Producto no encontrado con ID: " + idProducto));

        producto.setNombre(nuevoNombre);
        franquiciaRepository.save(franquicia);

        ProductoDTO respuesta = ProductoMapper.toDTO(producto);
        log.info("[actualizarNombreProducto] Actualizado: {}", respuesta);
        return respuesta;
    }
}