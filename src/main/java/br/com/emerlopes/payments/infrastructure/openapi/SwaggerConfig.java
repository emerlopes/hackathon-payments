package br.com.emerlopes.payments.infrastructure.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Título da API", version = "v1"),
        servers = {
                @Server(url = "http://localhost:8081", description = "Servidor Local"),
                @Server(url = "https//hackathon-payments:8081", description = "Servidor Docker")
        }
)
public class SwaggerConfig {
}