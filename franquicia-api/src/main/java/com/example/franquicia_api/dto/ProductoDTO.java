package com.example.franquicia_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductoDTO {

	@NotBlank(message = "El ID del producto es obligatorio")
	private String id;

	@NotBlank(message = "El nombre del producto es obligatorio")
	private String nombre;

	@Min(value = 0, message = "El stock debe ser mayor o igual a 0")
	private int stock;
}