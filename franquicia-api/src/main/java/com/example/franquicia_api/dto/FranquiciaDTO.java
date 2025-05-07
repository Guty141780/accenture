package com.example.franquicia_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class FranquiciaDTO {

	@NotBlank(message = "El ID de la franquicia es obligatorio")
	private String id;


    @NotBlank(message = "El nombre de la franquicia es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    private List<SucursalDTO> sucursales;
}