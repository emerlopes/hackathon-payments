package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    private CartaoDomainEntity cartaoDomainEntity;
    private ClienteDomainEntity clienteDomainEntity;
    private PagamentoDomainEntity pagamentoDomainEntity;

    @BeforeEach
    public void setup() {
        pagamentoDomainEntity = PagamentoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .valor(new BigDecimal("150.00"))
                .dataPagamento(LocalDateTime.now())
                .build();

        cartaoDomainEntity = CartaoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .limite(new BigDecimal("5000.00"))
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .pagamentos(List.of(pagamentoDomainEntity))
                .build();

        clienteDomainEntity = ClienteDomainEntity.builder()
                .id(UUID.randomUUID())
                .nome("Jorge Valdivia")
                .email("jorge.valdivia@example.com")
                .telefone("1234567890")
                .rua("Rua")
                .cidade("Cidade")
                .estado("EE")
                .cep("12345678")
                .pais("Pais")
                .cartoes(List.of(cartaoDomainEntity))
                .build();

        cartaoDomainEntity.setCliente(clienteDomainEntity);
    }

    @Test
    public void testConverterParaClienteEntity() {
        ClienteEntity clienteEntity = Converter.converterParaClienteEntity(cartaoDomainEntity);

        assertNotNull(clienteEntity);
        assertEquals(clienteDomainEntity.getId(), clienteEntity.getId());
        assertEquals(clienteDomainEntity.getNome(), clienteEntity.getNome());
        assertEquals(clienteDomainEntity.getEmail(), clienteEntity.getEmail());
        assertEquals(clienteDomainEntity.getTelefone(), clienteEntity.getTelefone());
        assertEquals(clienteDomainEntity.getRua(), clienteEntity.getRua());
        assertEquals(clienteDomainEntity.getCidade(), clienteEntity.getCidade());
        assertEquals(clienteDomainEntity.getEstado(), clienteEntity.getEstado());
        assertEquals(clienteDomainEntity.getCep(), clienteEntity.getCep());
        assertEquals(clienteDomainEntity.getPais(), clienteEntity.getPais());

        assertNotNull(clienteEntity.getCartoes());
        assertEquals(1, clienteEntity.getCartoes().size());
        CartaoEntity cartaoEntity = clienteEntity.getCartoes().get(0);
        assertEquals(cartaoDomainEntity.getId(), cartaoEntity.getId());
        assertEquals(cartaoDomainEntity.getCpf(), cartaoEntity.getCpf());
        assertEquals(cartaoDomainEntity.getNumero(), cartaoEntity.getNumero());
        assertEquals(cartaoDomainEntity.getLimite(), cartaoEntity.getLimite());
        assertEquals(cartaoDomainEntity.getDataValidade(), cartaoEntity.getDataValidade());
        assertEquals(cartaoDomainEntity.getCvv(), cartaoEntity.getCvv());

        assertNotNull(cartaoEntity.getPagamentos());
        assertEquals(1, cartaoEntity.getPagamentos().size());
        PagamentoEntity pagamentoEntity = cartaoEntity.getPagamentos().get(0);
        assertEquals(pagamentoDomainEntity.getId(), pagamentoEntity.getId());
        assertEquals(pagamentoDomainEntity.getCpf(), pagamentoEntity.getCpf());
        assertEquals(pagamentoDomainEntity.getNumero(), pagamentoEntity.getNumero());
        assertEquals(pagamentoDomainEntity.getDataValidade(), pagamentoEntity.getDataValidade());
        assertEquals(pagamentoDomainEntity.getCvv(), pagamentoEntity.getCvv());
        assertEquals(pagamentoDomainEntity.getValor(), pagamentoEntity.getValor());
        assertEquals(pagamentoDomainEntity.getDataPagamento(), pagamentoEntity.getDataPagamento());
    }
}