package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.SaldoBusinessExceptions;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class RegistrarPagamentoCartaoUseCaseImplTest {

    @Mock
    private PagamentoDomainRepository pagamentoDomainRepository;

    @Mock
    private CartaoDomainRepository cartaoDomainRepository;

    @InjectMocks
    private RegistrarPagamentoCartaoUseCaseImpl registrarPagamentoCartaoUseCase;

    private PagamentoDomainEntity pagamentoDomainEntity;
    private CartaoDomainEntity cartaoDomainEntity;

    @BeforeEach
    public void setup() {
        pagamentoDomainEntity = PagamentoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .valor(new BigDecimal("150.00"))
                .descricao("Pagamento de teste")
                .build();

        cartaoDomainEntity = CartaoDomainEntity.builder()
                .id(UUID.randomUUID())
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .limite(new BigDecimal("500.00"))
                .build();
    }

    @Test
    public void testExecutePagamentoComSucesso() {
        Mockito.when(cartaoDomainRepository.buscarCartaoClientePorNumero(any(CartaoDomainEntity.class)))
                .thenReturn(Optional.of(cartaoDomainEntity));
        Mockito.when(pagamentoDomainRepository.registrarPagamentoCartao(any(PagamentoDomainEntity.class)))
                .thenReturn(pagamentoDomainEntity);

        PagamentoDomainEntity resultado = registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);

        assertEquals(pagamentoDomainEntity, resultado);
    }

    @Test
    public void testExecuteCartaoNaoEncontrado() {
        Mockito.when(cartaoDomainRepository.buscarCartaoClientePorNumero(any(CartaoDomainEntity.class)))
                .thenReturn(Optional.empty());

        assertThrows(BusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }

    @Test
    public void testExecuteValorPagamentoInvalido() {
        pagamentoDomainEntity.setValor(BigDecimal.ZERO);

        assertThrows(BusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }

    @Test
    public void testExecuteDataValidadeInvalida() {
        pagamentoDomainEntity.setDataValidade(LocalDate.of(2024, 12, 31));

        assertThrows(BusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }

    @Test
    public void testExecuteCartaoVencido() {
        cartaoDomainEntity.setDataValidade(LocalDate.of(2020, 12, 31));

        Mockito.when(cartaoDomainRepository.buscarCartaoClientePorNumero(any(CartaoDomainEntity.class)))
                .thenReturn(Optional.of(cartaoDomainEntity));

        assertThrows(BusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }

    @Test
    public void testExecuteCvvInvalido() {
        pagamentoDomainEntity.setCvv("000");

        Mockito.when(cartaoDomainRepository.buscarCartaoClientePorNumero(any(CartaoDomainEntity.class)))
                .thenReturn(Optional.of(cartaoDomainEntity));

        assertThrows(BusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }

    @Test
    public void testExecuteSaldoInsuficiente() {
        pagamentoDomainEntity.setValor(new BigDecimal("600.00"));

        Mockito.when(cartaoDomainRepository.buscarCartaoClientePorNumero(any(CartaoDomainEntity.class)))
                .thenReturn(Optional.of(cartaoDomainEntity));

        assertThrows(SaldoBusinessExceptions.class, () -> {
            registrarPagamentoCartaoUseCase.execute(pagamentoDomainEntity);
        });
    }
}
