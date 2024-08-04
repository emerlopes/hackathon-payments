package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BuscarPagamentosCartaoUseCaseImplTest {

    @Mock
    private PagamentoDomainRepository pagamentoDomainRepository;

    @InjectMocks
    private BuscarPagamentosCartaoUseCaseImpl buscarPagamentosCartaoUseCase;

    private PagamentoDomainEntity pagamentoDomainEntity;

    @BeforeEach
    public void setup() {
        pagamentoDomainEntity = PagamentoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .valor(new BigDecimal("150.00"))
                .descricao("Pagamento de teste")
                .metodoPagamento("CREDITO")
                .statusPagamento("PAGO")
                .build();
    }

    @Test
    public void testExecute() {
        List<PagamentoDomainEntity> expectedPagamentos = Collections.singletonList(pagamentoDomainEntity);
        Mockito.when(pagamentoDomainRepository.buscarPagamentosPorCpf(any(PagamentoDomainEntity.class)))
                .thenReturn(expectedPagamentos);

        List<PagamentoDomainEntity> actualPagamentos = buscarPagamentosCartaoUseCase.execute(pagamentoDomainEntity);

        assertEquals(expectedPagamentos, actualPagamentos);
    }
}
