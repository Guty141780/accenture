package com.example.franquicia_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestasDTO<T> {
    private String mensaje;
    private T datos;
}