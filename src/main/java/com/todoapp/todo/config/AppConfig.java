package com.todoapp.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class for the Spring Boot application, defining beans and
 * configuring cross-origin resource sharing (CORS) settings.
 */
@Configuration
public class AppConfig {


    /**
     * Configures CORS (Cross-Origin Resource Sharing) for the /api/** endpoint.
     * Allows requests from http://localhost:5173/ with specific HTTP methods
     * and headers, and enables credentials (e.g., cookies) to be included in
     * the requests.
     *
     * <p>
     * <b>Note:</b> In a production environment, the {@code allowedOrigins} should
     * be explicitly set to the domain of your frontend application instead of
     * using a wildcard ("*") for security reasons.  Similarly, carefully
     * consider the implications of allowing credentials.
     * </p>
     *
     * @return A {@link WebMvcConfigurer} instance for configuring CORS settings.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:5173/")
                        .allowedMethods("GET", "POST", "DELETE", "PUT")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }


    /**
     * Configures a ModelMapper bean for object-to-object mapping.
     *
     * <p>
     * ModelMapper is a library that simplifies the process of mapping data
     * between different object types (e.g., entities and DTOs).
     * </p>
     *
     * @return A {@link ModelMapper} instance for performing object mapping.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
