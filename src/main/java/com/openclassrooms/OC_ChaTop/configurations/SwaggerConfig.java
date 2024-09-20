package com.openclassrooms.OC_ChaTop.configurations;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig class sets up Swagger for API documentation using OpenAPI.
 * It configures basic metadata like title, version, and description.
 */
@Configuration
public class SwaggerConfig {

    private SecurityScheme createAPIKeySecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
    /**
     * Configures the OpenAPI specification for Swagger UI.
     *
     * @return OpenAPI object with basic information about the API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Create an OpenAPI object and set its information
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeySecurityScheme()))
                .info(new Info()
                        .title("API Documentation") // Title of the API
                        .version("v1") // Version of the API
                        .description("This is the API documentation for the ChaTop application")); // Description of the API
    }
}
