package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/**")  // Ajuste para incluir apenas os caminhos que começam com /api/
                .packagesToScan("com.example.demo") // Ajuste para o pacote dos seus RestControllers
                .addOpenApiMethodFilter(method -> method.getDeclaringClass().isAnnotationPresent(RestController.class))
                .addOpenApiCustomizer(customOpenApi())
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Seminário 3 ADS/ESW")
                        .version("1.0.0")
                        .description("API de gerenciamento reservas de mesa em restaurante."))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    public OpenApiCustomizer customOpenApi() {
        return openApi -> {
            openApi.getInfo().setTitle("Seminário 3 ADS/ESW"); // Renomeia o título
            openApi.getInfo().setVersion("1.0.0"); // Define a versão
            openApi.getInfo().setDescription("API de gerenciamento reservas de mesa em restaurante."); // Define a descrição
        };
    }
}
