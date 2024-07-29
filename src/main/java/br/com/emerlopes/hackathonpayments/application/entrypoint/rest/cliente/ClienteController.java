package br.com.emerlopes.hackathonpayments.application.entrypoint.rest.cliente;

import br.com.emerlopes.hackathonpayments.application.entrypoint.rest.cartao.dto.GerarCartaoResponseDTO;
import br.com.emerlopes.hackathonpayments.application.entrypoint.rest.cliente.dto.RegistrarClienteRequestDTO;
import br.com.emerlopes.hackathonpayments.application.entrypoint.rest.cliente.dto.RegistrarClienteResponseDTO;
import br.com.emerlopes.hackathonpayments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.usecase.cliente.BuscarClientePorIdUseCase;
import br.com.emerlopes.hackathonpayments.domain.usecase.cliente.RegistrarClienteUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(ClienteController.class);
    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final BuscarClientePorIdUseCase buscarClientePorIdUseCase;

    public ClienteController(
            final RegistrarClienteUseCase registrarClienteUseCase,
            final BuscarClientePorIdUseCase buscarClientePorIdUseCase
    ) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.buscarClientePorIdUseCase = buscarClientePorIdUseCase;
    }

    @PostMapping
    @PreAuthorize("@securityService.isTokenValid(#token)")
    public ResponseEntity<?> registrarCliente(
            final @RequestHeader("Authorization") String token,
            final @RequestBody @Valid RegistrarClienteRequestDTO clienteRequestDTO
    ) {
        log.info("Recebendo requisicao para gerar cartao");
        final ClienteDomainEntity clienteDomainEntity = ClienteDomainEntity
                .builder()
                .cpf(clienteRequestDTO.getCpf())
                .nome(clienteRequestDTO.getNome())
                .email(clienteRequestDTO.getEmail())
                .telefone(clienteRequestDTO.getTelefone())
                .rua(clienteRequestDTO.getRua())
                .cidade(clienteRequestDTO.getCidade())
                .estado(clienteRequestDTO.getEstado())
                .cep(clienteRequestDTO.getCep())
                .pais(clienteRequestDTO.getPais())
                .build();

        final ClienteDomainEntity idCartaoGerado = registrarClienteUseCase.execute(clienteDomainEntity);

        log.info("Cliente registrado com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(
                GerarCartaoResponseDTO.builder()
                        .idCartao(idCartaoGerado.getId())
                        .build()
        );
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<?> buscarCliente(
            final @PathVariable UUID clienteId
    ) {
        log.info("Recebendo requisicao para buscar cliente");
        final ClienteDomainEntity clienteDomainEntity = ClienteDomainEntity
                .builder()
                .id(clienteId)
                .build();

        final ClienteDomainEntity idClienteGerado = buscarClientePorIdUseCase.execute(clienteDomainEntity);

        log.info("Cliente encontrado com sucesso");

        return ResponseEntity.status(HttpStatus.OK).body(
                RegistrarClienteResponseDTO.builder()
                        .idCliente(idClienteGerado.getId())
                        .build()
        );
    }
}
