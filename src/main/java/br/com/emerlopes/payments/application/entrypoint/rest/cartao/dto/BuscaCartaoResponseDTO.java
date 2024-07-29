package br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BuscaCartaoResponseDTO {

    @JsonProperty("id_cartao")
    private String idCartao;

    private String numero;
    private BigDecimal limite;
}
