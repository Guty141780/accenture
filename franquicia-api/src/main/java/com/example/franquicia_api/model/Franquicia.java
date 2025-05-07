package com.example.franquicia_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "franquicias")
public class Franquicia {
    @Id
    private String id;
    private String nombre;
    private List<Sucursal> sucursales;
}