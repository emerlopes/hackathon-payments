package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagamentoDomainRepositoryImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoDomainRepositoryImpl pagamentoDomainRepository;

    private PagamentoDomainEntity pagamentoDomainEntity;
    private PagamentoEntity pagamentoEntity;

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
                .metodoPagamento("CREDITO")
                .statusPagamento("PAGO")
                .dataPagamento(LocalDateTime.now())
                .build();

        pagamentoEntity = PagamentoEntity.builder()
                .id(pagamentoDomainEntity.getId())
                .cpf(pagamentoDomainEntity.getCpf())
                .numero(pagamentoDomainEntity.getNumero())
                .dataValidade(pagamentoDomainEntity.getDataValidade())
                .cvv(pagamentoDomainEntity.getCvv())
                .valor(pagamentoDomainEntity.getValor())
                .descricao(pagamentoDomainEntity.getDescricao())
                .metodoPagamento(pagamentoDomainEntity.getMetodoPagamento())
                .statusPagamento(pagamentoDomainEntity.getStatusPagamento())
                .dataPagamento(pagamentoDomainEntity.getDataPagamento())
                .build();
    }

    @Test
    public void testRegistrarPagamentoCartao() {
        Mockito.when(pagamentoRepository.save(any(PagamentoEntity.class))).thenReturn(pagamentoEntity);

        PagamentoDomainEntity resultado = pagamentoDomainRepository.registrarPagamentoCartao(pagamentoDomainEntity);

        assertEquals(pagamentoDomainEntity.getId(), resultado.getId());
        assertEquals(pagamentoDomainEntity.getCpf(), resultado.getCpf());
        assertEquals(pagamentoDomainEntity.getNumero(), resultado.getNumero());
        assertEquals(pagamentoDomainEntity.getDataValidade(), resultado.getDataValidade());
        assertEquals(pagamentoDomainEntity.getCvv(), resultado.getCvv());
        assertEquals(pagamentoDomainEntity.getValor(), resultado.getValor());
        assertEquals(pagamentoDomainEntity.getDescricao(), resultado.getDescricao());
        assertEquals(pagamentoDomainEntity.getMetodoPagamento(), resultado.getMetodoPagamento());
        assertEquals(pagamentoDomainEntity.getStatusPagamento(), resultado.getStatusPagamento());
        assertEquals(pagamentoDomainEntity.getDataPagamento(), resultado.getDataPagamento());
    }

    @Test
    public void testBuscarPagamentosPorCpf() {
        Mockito.when(pagamentoRepository.findByCpf(any(String.class))).thenReturn(Optional.of(Collections.singletonList(pagamentoEntity)));

        List<PagamentoDomainEntity> resultados = pagamentoDomainRepository.buscarPagamentosPorCpf(pagamentoDomainEntity);

        assertEquals(1, resultados.size());
        PagamentoDomainEntity resultado = resultados.get(0);
        assertEquals(pagamentoDomainEntity.getId(), resultado.getId());
        assertEquals(pagamentoDomainEntity.getCpf(), resultado.getCpf());
        assertEquals(pagamentoDomainEntity.getNumero(), resultado.getNumero());
        assertEquals(pagamentoDomainEntity.getDataValidade(), resultado.getDataValidade());
        assertEquals(pagamentoDomainEntity.getCvv(), resultado.getCvv());
        assertEquals(pagamentoDomainEntity.getValor(), resultado.getValor());
        assertEquals(pagamentoDomainEntity.getDescricao(), resultado.getDescricao());
        assertEquals(pagamentoDomainEntity.getMetodoPagamento(), resultado.getMetodoPagamento());
        assertEquals(pagamentoDomainEntity.getStatusPagamento(), resultado.getStatusPagamento());
        assertEquals(pagamentoDomainEntity.getDataPagamento(), resultado.getDataPagamento());
    }
}
