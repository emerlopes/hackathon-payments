package br.com.emerlopes.hackathonpayments.application.entrypoint.rest.cartao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GerarCartaoResponseDTO {
    @JsonProperty("id_cartao")
    private UUID idCartao;
}
