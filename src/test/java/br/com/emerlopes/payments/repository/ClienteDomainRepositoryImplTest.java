package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.application.exceptions.DatabasePersistenceException;
import br.com.emerlopes.payments.application.exceptions.ResourceNotFoundException;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class ClienteDomainRepositoryImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteDomainRepositoryImpl clienteDomainRepository;

    private ClienteDomainEntity clienteDomainEntity;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        clienteDomainEntity = ClienteDomainEntity.builder()
                .cpf("12345678900")
                .nome("Amanda do Rosario")
                .email("amanda.rosario@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .build();

        clienteEntity = ClienteEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Amanda do Rosario")
                .email("amanda.rosario@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .cartoes(List.of())
                .build();
    }

    @Test
    void testRegistrarCliente_Success() {
        Mockito.when(clienteRepository.save(Mockito.any(ClienteEntity.class)))
                .thenReturn(clienteEntity);

        ClienteDomainEntity result = clienteDomainRepository.registrarCliente(clienteDomainEntity);

        assertEquals(clienteEntity.getId(), result.getId());
        assertEquals(clienteEntity.getCpf(), result.getCpf());
        assertEquals(clienteEntity.getNome(), result.getNome());
        assertEquals(clienteEntity.getEmail(), result.getEmail());
        assertEquals(clienteEntity.getTelefone(), result.getTelefone());
        assertEquals(clienteEntity.getRua(), result.getRua());
        assertEquals(clienteEntity.getCidade(), result.getCidade());
        assertEquals(clienteEntity.getEstado(), result.getEstado());
        assertEquals(clienteEntity.getCep(), result.getCep());
        assertEquals(clienteEntity.getPais(), result.getPais());
    }

    @Test
    void testRegistrarCliente_Failure() {
        Mockito.when(clienteRepository.save(any(ClienteEntity.class)))
                .thenThrow(new RuntimeException("Erro ao registrar cliente"));

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            clienteDomainRepository.registrarCliente(clienteDomainEntity);
        });

        assertEquals("Erro ao registrar cliente", exception.getMessage());
    }

    @Test
    void testBuscarClientePorCpf_Success() {
        Mockito.when(clienteRepository.findByCpf(Mockito.any(String.class)))
                .thenReturn(Optional.of(clienteEntity));

        ClienteDomainEntity result = clienteDomainRepository.buscarClientePorCpf(clienteDomainEntity);

        assertEquals(clienteEntity.getId(), result.getId());
        assertEquals(clienteEntity.getCpf(), result.getCpf());
        assertEquals(clienteEntity.getNome(), result.getNome());
        assertEquals(clienteEntity.getEmail(), result.getEmail());
        assertEquals(clienteEntity.getTelefone(), result.getTelefone());
        assertEquals(clienteEntity.getRua(), result.getRua());
        assertEquals(clienteEntity.getCidade(), result.getCidade());
        assertEquals(clienteEntity.getEstado(), result.getEstado());
        assertEquals(clienteEntity.getCep(), result.getCep());
        assertEquals(clienteEntity.getPais(), result.getPais());
    }

    @Test
    void testBuscarClientePorCpf_NotFound() {
        Mockito.when(clienteRepository.findByCpf(Mockito.any(String.class)))
                .thenReturn(Optional.empty());

        ClienteDomainEntity result = clienteDomainRepository.buscarClientePorCpf(clienteDomainEntity);

        assertNull(result);
    }

    @Test
    void testBuscarClientes_Success() {
        List<ClienteEntity> clienteEntities = Stream.of(clienteEntity).collect(Collectors.toList());

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteEntities);

        List<ClienteDomainEntity> result = clienteDomainRepository.buscarClientes(clienteDomainEntity);

        assertEquals(1, result.size());
        ClienteDomainEntity resultEntity = result.get(0);
        assertEquals(clienteEntity.getId(), resultEntity.getId());
        assertEquals(clienteEntity.getCpf(), resultEntity.getCpf());
        assertEquals(clienteEntity.getNome(), resultEntity.getNome());
        assertEquals(clienteEntity.getEmail(), resultEntity.getEmail());
        assertEquals(clienteEntity.getTelefone(), resultEntity.getTelefone());
        assertEquals(clienteEntity.getRua(), resultEntity.getRua());
        assertEquals(clienteEntity.getCidade(), resultEntity.getCidade());
        assertEquals(clienteEntity.getEstado(), resultEntity.getEstado());
        assertEquals(clienteEntity.getCep(), resultEntity.getCep());
        assertEquals(clienteEntity.getPais(), resultEntity.getPais());
    }

    @Test
    void testBuscarClientes_NotFound() {
        Mockito.when(clienteRepository.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteDomainRepository.buscarClientes(clienteDomainEntity);
        });

        assertEquals("Nenhum cliente encontrado.", exception.getMessage());
    }

    @Test
    void testBuscarClientePorId_Success() {
        Mockito.when(clienteRepository.findById(eq(clienteDomainEntity.getId())))
                .thenReturn(Optional.of(clienteEntity));

        ClienteDomainEntity result = clienteDomainRepository.buscarClientePorId(clienteDomainEntity);

        assertEquals(clienteEntity.getId(), result.getId());
        assertEquals(clienteEntity.getCpf(), result.getCpf());
        assertEquals(clienteEntity.getNome(), result.getNome());
        assertEquals(clienteEntity.getEmail(), result.getEmail());
        assertEquals(clienteEntity.getTelefone(), result.getTelefone());
        assertEquals(clienteEntity.getRua(), result.getRua());
        assertEquals(clienteEntity.getCidade(), result.getCidade());
        assertEquals(clienteEntity.getEstado(), result.getEstado());
        assertEquals(clienteEntity.getCep(), result.getCep());
        assertEquals(clienteEntity.getPais(), result.getPais());
    }

    @Test
    void testBuscarClientePorId_NotFound() {
        Mockito.when(clienteRepository.findById(eq(clienteDomainEntity.getId())))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            clienteDomainRepository.buscarClientePorId(clienteDomainEntity);
        });

        assertEquals("Cliente nao encontrado", exception.getMessage());
    }
}