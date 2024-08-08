package br.com.emerlopes.payments.application.entrypoint.rest.cliente.examples;

import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(
        summary = "Registrar Cliente",
        description = "API para registrar um novo cliente",
        requestBody = @RequestBody(
                content = @Content(
                        schema = @Schema(implementation = RegistrarClienteRequestDTO.class),
                        examples = @ExampleObject(
                                name = "Exemplo de requisição de registro de cliente",
                                summary = "Exemplo de requisição de registro de cliente",
                                value = """
                                        {
                                            "cpf": "31321717512",
                                            "nome": "João Silva",
                                            "email": "joao.silva@example.com",
                                            "telefone": "11999999999",
                                            "rua": "Rua das Flores",
                                            "cidade": "São Paulo",
                                            "estado": "SP",
                                            "cep": "01001000",
                                            "pais": "Brasil"
                                        }
                                        """
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Resposta de sucesso",
                        content = @Content(
                                schema = @Schema(implementation = RegistrarClienteResponseDTO.class),
                                examples = @ExampleObject(
                                        name = "Chamada de sucesso",
                                        summary = "Exemplo de resposta de sucesso",
                                        value = """
                                                {
                                                    "id_cliente": "a3673b09-753c-4bc6-bb20-6a44be61ab26"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = @Content(
                                schema = @Schema(implementation = CustomErrorResponse.class),
                                examples = @ExampleObject(
                                        name = "Resposta não autorizada",
                                        summary = "Exemplo de resposta de erro de autorização",
                                        value = """
                                                {
                                                    "timestamp": "2023-08-07T14:00:00",
                                                    "message": "Invalid token",
                                                    "details": "/api/clientes"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal Server Error",
                        content = @Content(
                                schema = @Schema(implementation = CustomErrorResponse.class),
                                examples = @ExampleObject(
                                        name = "Resposta de erro interno do servidor",
                                        summary = "Exemplo de resposta de erro interno do servidor",
                                        value = """
                                                {
                                                    "timestamp": "2023-08-07T14:00:00",
                                                    "message": "An unexpected error occurred",
                                                    "details": "/api/clientes"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface RegistrarClienteExample {
}