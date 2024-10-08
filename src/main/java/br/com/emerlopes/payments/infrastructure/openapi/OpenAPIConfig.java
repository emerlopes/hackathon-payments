package br.com.emerlopes.payments.infrastructure.openapi;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Servico de pagamentos")
                        .version("1.0")
                        .description("Documentação da API de pagamentos")
                        .contact(new Contact().name("Seu Nome").email("lopes.ds@live.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}