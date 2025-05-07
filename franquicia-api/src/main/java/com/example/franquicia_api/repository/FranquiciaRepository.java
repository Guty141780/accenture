package com.example.franquicia_api.repository;

import com.example.franquicia_api.model.Franquicia;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FranquiciaRepository extends MongoRepository<Franquicia, String> {
}