package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegistrarPagamentoResponseDTO {
    @JsonProperty("chave_pagamento")
    private UUID chavePagamento;
}
