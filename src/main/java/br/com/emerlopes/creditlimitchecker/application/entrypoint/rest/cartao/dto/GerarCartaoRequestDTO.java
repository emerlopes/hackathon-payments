package br.com.emerlopes.creditlimitchecker.application.entrypoint.rest.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GerarCartaoRequestDTO {
    private String cpf;
    private BigDecimal limite;
    private String numero;

    @JsonProperty("data_validade")
    private LocalDate dataValidade;

    private String cvv;
}
