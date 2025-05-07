# Franquicia API

API REST desarrollada con Spring Boot y MongoDB para gestionar franquicias, sus sucursales y productos. La solución incluye control de stock, endpoints adicionales para actualización de nombres y está lista para ejecutarse en contenedores Docker.

---

## Tecnologías

- Java 17
- Spring Boot 3.4.6 (snapshot)
- Spring Data MongoDB
- Docker y Docker Compose
- Maven

---

## Estructura de la solución

```
franquicia-api/
├── src/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
└── .gitignore
```

---

## Cómo ejecutar la aplicación localmente

1. Compila el proyecto:

```
mvn clean package
```

2. Levanta la base de datos y la app:

```
Docker compose up
```

3. Accede a la API:

```
http://localhost:8080/api/franquicias
```

---

## Endpoints disponibles

| Método | Endpoint                                                      | Descripción                                    |
|--------|---------------------------------------------------------------|------------------------------------------------|
| POST   | `/api/franquicias`                                            | Crear una nueva franquicia                     |
| GET    | `/api/franquicias`                                            | Listar todas las franquicias                   |
| POST   | `/api/franquicias/{id}/sucursales`                            | Agregar una sucursal                           |
| POST   | `/api/franquicias/{id}/sucursales/{id}/productos`             | Agregar un producto a una sucursal             |
| PUT    | `/api/franquicias/{id}/sucursales/{id}/productos/{id}`        | Actualizar el stock de un producto             |
| DELETE | `/api/franquicias/{id}/sucursales/{id}/productos/{id}`        | Eliminar un producto de una sucursal           |
| GET    | `/api/franquicias/{id}/productos/max-stock`                   | Ver productos con más stock por sucursal       |
| PATCH  | `/api/franquicias/{id}/nombre`                                | Actualizar el nombre de una franquicia         |
| PATCH  | `/api/franquicias/{id}/sucursales/{id}/nombre`                | Actualizar el nombre de una sucursal           |
| PATCH  | `/api/franquicias/{id}/sucursales/{id}/productos/{id}/nombre` | Actualizar el nombre de un producto            |

---

## Buenas prácticas implementadas

- Uso de DTOs y mappers para separar lógica de presentación y entidades
- Validación con `jakarta.validation`
- Manejo de errores global con excepciones personalizadas
- Logs estructurados (`@Slf4j`) por operación
- Respuestas uniformes (`RespuestasDTO<T>`) con mensaje + datos
- Dockerización completa (MongoDB + API)
- Collection Postman disponible para pruebas

---

## Pruebas postman

Puedes importar y probar todos los endpoints desde esta colección:

[Descargar colección Postman](Accenture.postman_collection)



