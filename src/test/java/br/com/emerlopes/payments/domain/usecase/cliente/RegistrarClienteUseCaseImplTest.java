package br.com.emerlopes.payments.domain.usecase.cliente;

import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.repository.ClienteDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RegistrarClienteUseCaseImplTest {

    @Mock
    private ClienteDomainRepository clienteDomainRepository;

    @InjectMocks
    private RegistrarClienteUseCaseImpl registrarClienteUseCase;

    private ClienteDomainEntity clienteDomainEntity;

    @BeforeEach
    void setUp() {
        clienteDomainEntity = ClienteDomainEntity.builder()
                .cpf("12345678900")
                .nome("Oliver Queen")
                .email("oliver.queen@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();
    }

    @Test
    void testExecute_Success() {
        ClienteDomainEntity savedClienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Oliver Queen")
                .email("oliver.queen@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();

        Mockito.when(clienteDomainRepository.buscarClientePorCpf(any(ClienteDomainEntity.class)))
                .thenReturn(null);
        Mockito.when(clienteDomainRepository.registrarCliente(any(ClienteDomainEntity.class)))
                .thenReturn(savedClienteDomainEntity);

        ClienteDomainEntity result = registrarClienteUseCase.execute(clienteDomainEntity);

        assertEquals(savedClienteDomainEntity.getId(), result.getId());
        assertEquals(savedClienteDomainEntity.getCpf(), result.getCpf());
        assertEquals(savedClienteDomainEntity.getNome(), result.getNome());
        assertEquals(savedClienteDomainEntity.getEmail(), result.getEmail());
    }

    @Test
    void testExecute_ClienteJaRegistrado() {
        ClienteDomainEntity existingClienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Oliver Queen")
                .email("oliver.queen@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();

        Mockito.when(clienteDomainRepository.buscarClientePorCpf(any(ClienteDomainEntity.class)))
                .thenReturn(existingClienteDomainEntity);

        BusinessExceptions exception = assertThrows(BusinessExceptions.class, () -> {
            registrarClienteUseCase.execute(clienteDomainEntity);
        });

        assertEquals("Cliente já registrado", exception.getMessage());
    }
}