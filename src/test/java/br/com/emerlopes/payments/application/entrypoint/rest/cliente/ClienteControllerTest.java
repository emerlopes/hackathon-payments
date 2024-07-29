package br.com.emerlopes.payments.application.entrypoint.rest.cliente;

import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.BuscarClienteResponseDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteRequestDTO;
import br.com.emerlopes.payments.application.entrypoint.rest.cliente.dto.RegistrarClienteResponseDTO;
import br.com.emerlopes.payments.application.shared.CustomResponseDTO;
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
        CustomResponseDTO<RegistrarClienteResponseDTO> responseBody =
                (CustomResponseDTO<RegistrarClienteResponseDTO>) responseEntity.getBody();
        assertEquals(savedClienteDomainEntity.getId(), responseBody.getData().getIdCliente());
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
        CustomResponseDTO<BuscarClienteResponseDTO> responseBody =
                (CustomResponseDTO<BuscarClienteResponseDTO>) responseEntity.getBody();
        assertEquals(clienteDomainEntity.getId(), responseBody.getData().getIdCliente());
        assertEquals(clienteDomainEntity.getNome(), responseBody.getData().getNome());
        assertEquals(clienteDomainEntity.getEmail(), responseBody.getData().getEmail());
    }
}