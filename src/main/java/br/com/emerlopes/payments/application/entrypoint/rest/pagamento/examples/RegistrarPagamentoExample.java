package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.examples;

import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoResponseDTO;
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
        summary = "Registrar Pagamento de Cartão",
        description = "API para registrar um pagamento com cartão de crédito",
        requestBody = @RequestBody(
                content = @Content(
                        schema = @Schema(implementation = RegistrarPagamentoRequestDTO.class),
                        examples = @ExampleObject(
                                name = "Exemplo de requisição de pagamento",
                                summary = "Exemplo de requisição de pagamento",
                                value = """
                                        {
                                            "cpf": "31321717512",
                                            "numero": "1234567890123457",
                                            "data_validade": "2024-10-01",
                                            "cvv": "123",
                                            "valor": 1050.68,
                                            "descricao": "Carbonite web goalkeeper gloves are ergonomically designed to give easy fit"
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
                                schema = @Schema(implementation = RegistrarPagamentoResponseDTO.class),
                                examples = @ExampleObject(
                                        name = "Chamada de sucesso",
                                        summary = "Exemplo de resposta de sucesso",
                                        value = """
                                                {
                                                    "chave_pagamento": "a3673b09-753c-4bc6-bb20-6a44be61ab26"
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
                                                    "details": "/api/pagamentos"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "402",
                        description = "Payment Required",
                        content = @Content(
                                schema = @Schema(implementation = CustomErrorResponse.class),
                                examples = @ExampleObject(
                                        name = "Saldo limite excedido",
                                        summary = "Exemplo de resposta de saldo limite excedido",
                                        value = """
                                                {
                                                    "timestamp": "2023-08-07T14:00:00",
                                                    "message": "Saldo limite excedido",
                                                    "details": "/api/pagamentos/cliente/{cpf}"
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
                                                    "details": "/api/pagamentos"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface RegistrarPagamentoExample {
}
