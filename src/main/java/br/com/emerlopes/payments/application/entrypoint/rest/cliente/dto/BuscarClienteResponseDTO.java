package br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BuscarClienteResponseDTO {
    @JsonProperty("id_cliente")
    private UUID idCliente;
    private String nome;
    private String email;
}
