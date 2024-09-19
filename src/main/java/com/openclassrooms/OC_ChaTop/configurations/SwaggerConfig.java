package com.openclassrooms.OC_ChaTop.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig class sets up Swagger for API documentation using OpenAPI.
 * It configures basic metadata like title, version, and description.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configures the OpenAPI specification for Swagger UI.
     *
     * @return OpenAPI object with basic information about the API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // Create an OpenAPI object and set its information
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation") // Title of the API
                        .version("v1") // Version of the API
                        .description("This is the API documentation for the ChaTop application")); // Description of the API
    }
}
