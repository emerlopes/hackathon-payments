package br.com.emerlopes.payments.infrastructure.integrations.hackathonauth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AutenticacaoResponseDTO {
    @JsonProperty("token_acesso")
    private String tokenAcesso;


    @JsonCreator
    public AutenticacaoResponseDTO(@JsonProperty("token_acesso") String tokenAcesso) {
        this.tokenAcesso = tokenAcesso;
    }

}
