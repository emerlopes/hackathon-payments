package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.examples;

import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.BuscarPagamentosCartaoResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Operation(
        summary = "Buscar Pagamentos do Cartão",
        description = "API para buscar todos os pagamentos de cartão associados a um cliente específico pelo CPF",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Resposta de sucesso",
                        content = @Content(
                                schema = @Schema(implementation = BuscarPagamentosCartaoResponseDTO.class),
                                examples = @ExampleObject(
                                        name = "Chamada de sucesso",
                                        summary = "Exemplo de resposta de sucesso",
                                        value = """
                                                [
                                                    {
                                                        "valor": 1050.68,
                                                        "descricao": "Compra de material esportivo",
                                                        "metodo_pagamento": "CARTAO_CREDITO",
                                                        "status_pagamento": "PROCESSANDO"
                                                    }
                                                ]
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
                                                    "details": "/api/pagamentos/cliente/{cpf}"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface BuscarPagamentosCartaoExample {
}
