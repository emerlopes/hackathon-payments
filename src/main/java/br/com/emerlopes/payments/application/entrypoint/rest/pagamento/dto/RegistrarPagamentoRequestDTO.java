package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RegistrarPagamentoRequestDTO {
    private String cpf;
    private String numero;

    @JsonProperty("data_validade")
    private LocalDate dataValidade;

    private String cvv;
    private BigDecimal valor;
    private String descricao;
}
