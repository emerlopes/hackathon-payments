package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto;

import br.com.emerlopes.payments.application.shared.enums.MetodoPagamentoEnum;
import br.com.emerlopes.payments.application.shared.enums.StatusPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BuscarPagamentosCartaoResponseDTO {
    private BigDecimal valor;
    private String descricao;

    @JsonProperty("metodo_pagamento")
    private MetodoPagamentoEnum metodoPagamento;

    @JsonProperty("status_pagamento")
    private StatusPagamentoEnum statusPagamento;
}
