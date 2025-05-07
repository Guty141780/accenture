package com.example.franquicia_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActualizarNombreDTO {

    @NotBlank(message = "El nuevo nombre no puede estar vacío")
    private String nuevoNombre;
}