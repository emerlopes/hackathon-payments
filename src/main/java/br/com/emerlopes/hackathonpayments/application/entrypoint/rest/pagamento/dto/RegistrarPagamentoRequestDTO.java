package br.com.emerlopes.hackathonpayments.application.entrypoint.rest.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RegistrarPagamentoRequestDTO {
    private String cpf;
    private String numero;

    @JsonProperty("data_validade")
    private String dataValidade;

    private String cvv;
    private BigDecimal valor;
}
