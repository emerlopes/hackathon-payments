package br.com.emerlopes.payments.domain.usecase.cliente;

import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
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
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BuscarClientePorIdUseCaseImplTest {

    @Mock
    private ClienteDomainRepository clienteDomainRepository;

    @InjectMocks
    private BuscarClientePorIdUseCaseImpl buscarClientePorIdUseCase;

    private ClienteDomainEntity clienteDomainEntity;

    @BeforeEach
    void setUp() {
        clienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Felipe Rocha")
                .email("felipe.rocha@example.com")
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
        Mockito.when(clienteDomainRepository.buscarClientePorId(any(ClienteDomainEntity.class)))
                .thenReturn(clienteDomainEntity);

        ClienteDomainEntity result = buscarClientePorIdUseCase.execute(clienteDomainEntity);

        assertEquals(clienteDomainEntity.getId(), result.getId());
        assertEquals(clienteDomainEntity.getCpf(), result.getCpf());
        assertEquals(clienteDomainEntity.getNome(), result.getNome());
        assertEquals(clienteDomainEntity.getEmail(), result.getEmail());
        assertEquals(clienteDomainEntity.getTelefone(), result.getTelefone());
        assertEquals(clienteDomainEntity.getRua(), result.getRua());
        assertEquals(clienteDomainEntity.getCidade(), result.getCidade());
        assertEquals(clienteDomainEntity.getEstado(), result.getEstado());
        assertEquals(clienteDomainEntity.getCep(), result.getCep());
        assertEquals(clienteDomainEntity.getPais(), result.getPais());
    }
}