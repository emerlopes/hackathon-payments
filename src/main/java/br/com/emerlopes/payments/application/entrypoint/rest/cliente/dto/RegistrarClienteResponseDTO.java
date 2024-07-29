package br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RegistrarClienteResponseDTO {
    @JsonProperty("id_cliente")
    private UUID idCliente;
}
