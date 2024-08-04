package br.com.emerlopes.payments.application.entrypoint.rest.cliente;

import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.BuscarClienteResponseDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteResponseDTO;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.usecase.cliente.BuscarClientePorIdUseCase;
import br.com.emerlopes.payments.domain.usecase.cliente.RegistrarClienteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private RegistrarClienteUseCase registrarClienteUseCase;

    @Mock
    private BuscarClientePorIdUseCase buscarClientePorIdUseCase;

    @InjectMocks
    private ClienteController clienteController;

    @Test
    void testRegistrarCliente() {
        RegistrarClienteRequestDTO requestDTO = RegistrarClienteRequestDTO.builder()
                .cpf("12345678900")
                .nome("Reinaldo Rossi")
                .email("reginaldo.rossi@example.com")
                .telefone("1234567890")
                .rua("Rua Exemplo")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();

        ClienteDomainEntity savedClienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Reinaldo Rossi")
                .email("reginaldo.rossi@example.com")
                .telefone("1234567890")
                .rua("Rua Exemplo")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();

        Mockito.when(registrarClienteUseCase.execute(Mockito.any(ClienteDomainEntity.class)))
                .thenReturn(savedClienteDomainEntity);

        ResponseEntity<?> responseEntity = clienteController.registrarCliente("Bearer token", requestDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        RegistrarClienteResponseDTO responseBody = (RegistrarClienteResponseDTO) responseEntity.getBody();
        assert responseBody != null;
        assertEquals(savedClienteDomainEntity.getId(), responseBody.getIdCliente());
    }

    @Test
    void testBuscarCliente() {
        UUID clienteId = UUID.randomUUID();
        ClienteDomainEntity clienteDomainEntity = ClienteDomainEntity.builder()
                .id(clienteId)
                .build();

        Mockito.when(buscarClientePorIdUseCase.execute(Mockito.any(ClienteDomainEntity.class)))
                .thenReturn(clienteDomainEntity);

        ResponseEntity<?> responseEntity = clienteController.buscarCliente("Bearer token", clienteId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        BuscarClienteResponseDTO responseBody = (BuscarClienteResponseDTO) responseEntity.getBody();
        assert responseBody != null;
        assertEquals(clienteDomainEntity.getId(), responseBody.getIdCliente());
        assertEquals(clienteDomainEntity.getNome(), responseBody.getNome());
        assertEquals(clienteDomainEntity.getEmail(), responseBody.getEmail());
    }
}