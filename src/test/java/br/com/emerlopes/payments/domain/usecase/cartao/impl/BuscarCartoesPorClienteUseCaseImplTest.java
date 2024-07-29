package br.com.emerlopes.payments.domain.usecase.cartao.impl;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BuscarCartoesPorClienteUseCaseImplTest {

    @Mock
    private CartaoDomainRepository cartaoDomainRepository;

    @InjectMocks
    private BuscarCartoesPorClienteUseCaseImpl buscarCartoesPorClienteUseCase;

    private CartaoDomainEntity cartaoDomainEntity;

    @BeforeEach
    public void setup() {
        cartaoDomainEntity = CartaoDomainEntity.builder()
                .id(UUID.randomUUID())
                .idCliente(UUID.randomUUID())
                .cpf("12345678900")
                .limite(new BigDecimal("1500.00"))
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .build();
    }

    @Test
    public void testExecute_Success() {
        List<CartaoDomainEntity> cartoes = Collections.singletonList(cartaoDomainEntity);
        Mockito.when(cartaoDomainRepository.buscarCartoesClientePorId(any(CartaoDomainEntity.class)))
                .thenReturn(cartoes);

        List<CartaoDomainEntity> result = buscarCartoesPorClienteUseCase.execute(cartaoDomainEntity);

        assertEquals(1, result.size());
        assertEquals(cartaoDomainEntity.getId(), result.get(0).getId());
        assertEquals(cartaoDomainEntity.getCpf(), result.get(0).getCpf());
        assertEquals(cartaoDomainEntity.getNumero(), result.get(0).getNumero());
        assertEquals(cartaoDomainEntity.getLimite(), result.get(0).getLimite());
        assertEquals(cartaoDomainEntity.getDataValidade(), result.get(0).getDataValidade());
        assertEquals(cartaoDomainEntity.getCvv(), result.get(0).getCvv());
        assertEquals(cartaoDomainEntity.getIdCliente(), result.get(0).getIdCliente());
    }

    @Test
    public void testExecute_NoCardsFound() {
        Mockito.when(cartaoDomainRepository.buscarCartoesClientePorId(any(CartaoDomainEntity.class)))
                .thenReturn(Collections.emptyList());

        List<CartaoDomainEntity> result = buscarCartoesPorClienteUseCase.execute(cartaoDomainEntity);

        assertTrue(result.isEmpty());
    }
}