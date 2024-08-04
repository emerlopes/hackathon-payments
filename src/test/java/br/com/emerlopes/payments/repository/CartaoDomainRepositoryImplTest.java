package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.application.exceptions.DatabasePersistenceException;
import br.com.emerlopes.payments.application.exceptions.ResourceNotFoundException;
import br.com.emerlopes.payments.application.shared.CartaoUtils;
import br.com.emerlopes.payments.application.shared.CpfUtils;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class CartaoDomainRepositoryImplTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoDomainRepositoryImpl cartaoDomainRepository;

    private CartaoDomainEntity cartaoDomainEntity;
    private CartaoEntity cartaoEntity;
    private ClienteDomainEntity clienteDomainEntity;
    private ClienteEntity clienteEntity;

    @BeforeEach
    public void setup() {
        clienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("")
                .email("john.doe@example.com")
                .telefone("1234567890")
                .rua("Rua Exemplo")
                .cidade("Cidade Exemplo")
                .estado("EE")
                .cep("12345678")
                .pais("Pais Exemplo")
                .cartoes(Collections.emptyList())
                .build();

        cartaoDomainEntity = CartaoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .limite(new BigDecimal("1500.00"))
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .cliente(clienteDomainEntity)
                .build();

        cartaoEntity = CartaoEntity.builder()
                .id(cartaoDomainEntity.getId())
                .cpf(cartaoDomainEntity.getCpf())
                .numero(cartaoDomainEntity.getNumero())
                .limite(cartaoDomainEntity.getLimite())
                .dataValidade(cartaoDomainEntity.getDataValidade())
                .cvv(cartaoDomainEntity.getCvv())
                .cliente(Converter.converterParaClienteEntity(cartaoDomainEntity))
                .build();

        clienteEntity = Converter.converterParaClienteEntity(cartaoDomainEntity);
    }

    @Test
    public void testGerarCartao_Success() {
        Mockito.when(cartaoRepository.save(any(CartaoEntity.class))).thenReturn(cartaoEntity);

        CartaoDomainEntity result = cartaoDomainRepository.gerarCartao(cartaoDomainEntity);

        assertNotNull(result);
        assertEquals(cartaoEntity.getId(), result.getId());
    }

    @Test
    public void testGerarCartao_Error() {
        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.gerarCartao(cartaoDomainEntity);
        });

        assertEquals("Erro ao salvar cartao", exception.getMessage());
    }

    @Test
    public void testJaPossuiDoisCartoes_Sim() {
        CartaoEntity cartao1 = CartaoEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .numero("1234567890123456")
                .limite(new BigDecimal("1500.00"))
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .build();

        CartaoEntity cartao2 = CartaoEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .numero("1234567890123456")
                .limite(new BigDecimal("1500.00"))
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .build();

        List<CartaoEntity> cartoes = Arrays.asList(cartao1, cartao2);
        Mockito.when(cartaoRepository.findByCpf(any(String.class))).thenReturn(Optional.of(cartoes));

        boolean result = cartaoDomainRepository.jaPossuiDoisCartoes(cartaoDomainEntity);

        assertTrue(result);
    }

    @Test
    public void testJaPossuiDoisCartoes_Nao() {
        List<CartaoEntity> cartoes = Collections.singletonList(
                CartaoEntity.builder()
                        .id(UUID.randomUUID())
                        .cpf("12345678900")
                        .numero("1234567890123456")
                        .limite(new BigDecimal("1500.00"))
                        .dataValidade(LocalDate.of(2025, 12, 31))
                        .cvv("123")
                        .build()
        );
        Mockito.when(cartaoRepository.findByCpf(any(String.class))).thenReturn(Optional.of(cartoes));

        boolean result = cartaoDomainRepository.jaPossuiDoisCartoes(cartaoDomainEntity);

        assertFalse(result);
    }

    @Test
    public void testJaPossuiDoisCartoes_Nenhum_Cartao() {
        Mockito.when(cartaoRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartaoDomainRepository.jaPossuiDoisCartoes(cartaoDomainEntity);
        });

        assertEquals("Nenhum cartao encontrado para o cliente", exception.getMessage());
    }

    @Test
    public void testBuscarCartaoPorId_Success() {
        Mockito.when(cartaoRepository.findById(any(UUID.class))).thenReturn(Optional.of(cartaoEntity));

        CartaoDomainEntity result = cartaoDomainRepository.buscarCartaoPorId(cartaoDomainEntity);

        assertNotNull(result);
        assertEquals(cartaoEntity.getId(), result.getId());
    }

    @Test
    public void testBuscarCartaoPorId_CartaoNaoEncontrado() {
        Mockito.when(cartaoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartaoPorId(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartao por id", exception.getMessage());
    }

    @Test
    public void testBuscarCartaoPorId_ErroInesperado() {
        Mockito.when(cartaoRepository.findById(any(UUID.class))).thenThrow(new RuntimeException("Erro inesperado"));

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartaoPorId(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartao por id", exception.getMessage());
    }

    @Test
    public void testBuscarCartoesClientePorId_Success() {
        List<CartaoEntity> cartoes = Arrays.asList(cartaoEntity);
        Mockito.when(cartaoRepository.findByClienteId(eq(cartaoDomainEntity.getIdCliente()))).thenReturn(Optional.of(cartoes));

        List<CartaoDomainEntity> result = cartaoDomainRepository.buscarCartoesClientePorId(cartaoDomainEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cartaoEntity.getId(), result.get(0).getId());
        assertEquals(CartaoUtils.mascararCartaoCredito(cartaoEntity.getNumero()), result.get(0).getNumero());
    }

    @Test
    public void testBuscarCartoesClientePorId_NenhumCartao() {
        Mockito.when(cartaoRepository.findByClienteId(any(UUID.class))).thenReturn(Optional.empty());

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartoesClientePorId(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartoes por cliente", exception.getMessage());
    }

    @Test
    public void testBuscarCartoesClientePorId_ErroInesperado() {
        Mockito.when(cartaoRepository.findByClienteId(any(UUID.class))).thenThrow(new RuntimeException("Erro inesperado"));

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartoesClientePorId(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartoes por cliente", exception.getMessage());
    }

    @Test
    public void testBuscarCartoesClientePorCpf_Success() {
        List<CartaoEntity> cartoes = Arrays.asList(cartaoEntity);
        Mockito.when(cartaoRepository.findByCpf(eq(cartaoDomainEntity.getCpf()))).thenReturn(Optional.of(cartoes));

        List<CartaoDomainEntity> result = cartaoDomainRepository.buscarCartoesClientePorCpf(cartaoDomainEntity);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cartaoEntity.getId(), result.get(0).getId());
        assertEquals(CartaoUtils.mascararCartaoCredito(cartaoEntity.getNumero()), result.get(0).getNumero());
    }

    @Test
    public void testBuscarCartoesClientePorCpf_NenhumCartao() {
        Mockito.when(cartaoRepository.findByCpf(any(String.class))).thenReturn(Optional.empty());

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartoesClientePorCpf(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartoes por cliente", exception.getMessage());
    }

    @Test
    public void testBuscarCartoesClientePorCpf_ErroInesperado() {
        Mockito.when(cartaoRepository.findByCpf(any(String.class))).thenThrow(new RuntimeException("Erro inesperado"));

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartoesClientePorCpf(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartoes por cliente", exception.getMessage());
    }

    @Test
    public void testBuscarCartaoClientePorNumero_Success() {
        Mockito.when(cartaoRepository.findByNumero(any(String.class))).thenReturn(Optional.of(cartaoEntity));

        Optional<CartaoDomainEntity> result = cartaoDomainRepository.buscarCartaoClientePorNumero(cartaoDomainEntity);

        assertTrue(result.isPresent());
        assertEquals(cartaoEntity.getId(), result.get().getId());
        assertEquals(CartaoUtils.mascararCartaoCredito(cartaoEntity.getNumero()), result.get().getNumero());
    }

    @Test
    public void testBuscarCartaoClientePorNumero_NenhumCartao() {
        Mockito.when(cartaoRepository.findByNumero(any(String.class))).thenReturn(Optional.empty());

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartaoClientePorNumero(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartao por numero", exception.getMessage());
    }

    @Test
    public void testBuscarCartaoClientePorNumero_ErroInesperado() {
        Mockito.when(cartaoRepository.findByNumero(any(String.class))).thenThrow(new RuntimeException("Erro inesperado"));

        DatabasePersistenceException exception = assertThrows(DatabasePersistenceException.class, () -> {
            cartaoDomainRepository.buscarCartaoClientePorNumero(cartaoDomainEntity);
        });

        assertEquals("Erro nao esperado ao buscar cartao por numero", exception.getMessage());
    }

    @Test
    public void testAtualizarParaNovoLimiteCartaoPorNumero_Success() {
        Mockito.when(cartaoRepository.findByNumero(any(String.class))).thenReturn(Optional.of(cartaoEntity));

        CartaoDomainEntity novoCartaoDomainEntity = CartaoDomainEntity.builder()
                .numero(cartaoEntity.getNumero())
                .limite(new BigDecimal("2000.00"))
                .build();

        cartaoDomainRepository.atualizarParaNovoLimiteCartaoPorNumero(novoCartaoDomainEntity);

        Mockito.verify(cartaoRepository, Mockito.times(1)).save(any(CartaoEntity.class));
    }

    @Test
    public void testAtualizarParaNovoLimiteCartaoPorNumero_NenhumCartao() {
        Mockito.when(cartaoRepository.findByNumero(any(String.class))).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            cartaoDomainRepository.atualizarParaNovoLimiteCartaoPorNumero(cartaoDomainEntity);
        });

        assertEquals("Cartao nao encontrado", exception.getMessage());
    }
}
