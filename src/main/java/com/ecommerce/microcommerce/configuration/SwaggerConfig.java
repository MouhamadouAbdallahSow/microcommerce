package com.ecommerce.microcommerce.configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microcommerce API")
                        .version("1.0")
                        .description("Documentation de l'API Microcommerce")
                        .contact(new Contact()
                                .name("Mamadou Sow")
                                .email("mamadou@example.com"))
                        .license(new License()
                                .name("Licence API")
                                .url("http://example.com/license")));
    }
}