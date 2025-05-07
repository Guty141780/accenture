# Imagen base de Java
FROM openjdk:17-jdk-slim

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR compilado
COPY target/franquicia-api-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto por el que corre Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]