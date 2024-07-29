package br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {
    @JsonProperty("token_valido")
    private boolean tokenValido;

    @JsonCreator
    public TokenResponseDTO(@JsonProperty("token_valido") boolean tokenValido) {
        this.tokenValido = tokenValido;
    }
}
