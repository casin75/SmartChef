package com.example.smartchefjava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Datos generales de la página de Swagger: http://localhost:8080/swagger-ui.html
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiSmartChef() {
        return new OpenAPI().info(new Info()
                .title("API de SmartChef")
                .version("1.0")
                .description("Recetas, despensa, colecciones e historial de cocina de SmartChef"));
    }
}
