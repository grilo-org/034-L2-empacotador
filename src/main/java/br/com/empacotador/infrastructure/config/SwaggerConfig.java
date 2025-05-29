package br.com.empacotador.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
@OpenAPIDefinition(info = @Info(title = "API de Empacotamento", version = "1.0"))
public class SwaggerConfig {}
