package br.com.emerlopes.payments.domain.usecase.cartao.impl;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.CartaoBusinessExceptions;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.repository.ClienteDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class GerarCartaoUseCaseImplTest {

    @Mock
    private CartaoDomainRepository cartaoDomainRepository;

    @Mock
    private ClienteDomainRepository clienteDomainRepository;

    @InjectMocks
    private GerarCartaoUseCaseImpl gerarCartaoUseCase;

    private CartaoDomainEntity cartaoDomainEntity;
    private ClienteDomainEntity clienteDomainEntity;

    @BeforeEach
    public void setup() {
        clienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .nome("Roberto Souza")
                .email("roberto.souza@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
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
    }

    @Test
    public void testExecute_Success() {
        Mockito.when(cartaoDomainRepository.jaPossuiDoisCartoes(any(CartaoDomainEntity.class)))
                .thenReturn(false);
        Mockito.when(clienteDomainRepository.buscarClientePorCpf(any(ClienteDomainEntity.class)))
                .thenReturn(clienteDomainEntity);
        Mockito.when(cartaoDomainRepository.gerarCartao(any(CartaoDomainEntity.class)))
                .thenReturn(cartaoDomainEntity);

        CartaoDomainEntity result = gerarCartaoUseCase.execute(cartaoDomainEntity);

        assertNotNull(result);
        assertEquals(cartaoDomainEntity.getId(), result.getId());
        assertEquals(cartaoDomainEntity.getCpf(), result.getCpf());
        assertEquals(cartaoDomainEntity.getNumero(), result.getNumero());
        assertEquals(cartaoDomainEntity.getLimite(), result.getLimite());
        assertEquals(cartaoDomainEntity.getDataValidade(), result.getDataValidade());
        assertEquals(cartaoDomainEntity.getCvv(), result.getCvv());
        assertEquals(cartaoDomainEntity.getCliente().getId(), result.getCliente().getId());
    }

    @Test
    public void testExecute_ClienteJaPossuiDoisCartoes() {
        Mockito.when(cartaoDomainRepository.jaPossuiDoisCartoes(any(CartaoDomainEntity.class)))
                .thenReturn(true);

        CartaoBusinessExceptions exception = assertThrows(CartaoBusinessExceptions.class, () -> {
            gerarCartaoUseCase.execute(cartaoDomainEntity);
        });

        assertEquals("Cliente ja possui dois cartoes", exception.getMessage());
    }

    @Test
    public void testExecute_ClienteNaoEncontrado() {
        Mockito.when(cartaoDomainRepository.jaPossuiDoisCartoes(any(CartaoDomainEntity.class)))
                .thenReturn(false);
        Mockito.when(clienteDomainRepository.buscarClientePorCpf(any(ClienteDomainEntity.class)))
                .thenReturn(null);

        BusinessExceptions exception = assertThrows(BusinessExceptions.class, () -> {
            gerarCartaoUseCase.execute(cartaoDomainEntity);
        });

        assertEquals("Só é possível registrar um novo cartão para um cliente já existente.", exception.getMessage());
    }
}