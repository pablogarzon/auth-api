package com.example.authapi.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.authapi.controllers.ExceptionController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @SuppressWarnings("deprecation")
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.authapi.controllers")
            	       .and(handler -> handler.declaringClass() != ExceptionController.class)) 
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(List.of(apiKey()))
            .securityContexts(List.of(securityContext()));
    }

    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    @SuppressWarnings("deprecation")
	private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/users/.*")) // aplica a todos los endpoints de users
            .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("Bearer", new AuthorizationScope[]{scope}));
    }
}