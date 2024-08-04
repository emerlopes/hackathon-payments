package br.com.emerlopes.payments.application.entrypoint.rest.pagamento;

import br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto.RegistrarPagamentoRequestDTO;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.pagamento.BuscarPagamentosCartaoUseCase;
import br.com.emerlopes.payments.domain.usecase.pagamento.RegistrarPagamentoCartaoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarPagamentoCartaoUseCase registrarPagamentoCartaoUseCase;

    @Mock
    private BuscarPagamentosCartaoUseCase buscarPagamentosCartaoUseCase;

    @InjectMocks
    private PagamentoController pagamentoController;

    private RegistrarPagamentoRequestDTO registrarPagamentoRequestDTO;
    private PagamentoDomainEntity pagamentoDomainEntity;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pagamentoController).build();

        registrarPagamentoRequestDTO = RegistrarPagamentoRequestDTO.builder()
                .cpf("12345678900")
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .valor(new BigDecimal("150.00"))
                .descricao("Pagamento de teste")
                .build();

        pagamentoDomainEntity = PagamentoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .valor(new BigDecimal("150.00"))
                .descricao("Pagamento de teste")
                .build();
    }

    @Test
    public void testRegistrarPagamentoCartao() throws Exception {
        Mockito.when(registrarPagamentoCartaoUseCase.execute(any(PagamentoDomainEntity.class)))
                .thenReturn(pagamentoDomainEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ObjectNode pagamentoRequestJsonNode = objectMapper.valueToTree(registrarPagamentoRequestDTO);
        pagamentoRequestJsonNode.put("data_validade", "12/25"); // Enviando a data no formato MM/yy
        String pagamentoRequestJson = objectMapper.writeValueAsString(pagamentoRequestJsonNode);

        mockMvc.perform(post("/api/pagamentos")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pagamentoRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chave_pagamento").value(pagamentoDomainEntity.getId().toString()));
    }

    @Test
    public void testBuscarPagamentosCartao() throws Exception {
        List<PagamentoDomainEntity> pagamentos = Collections.singletonList(pagamentoDomainEntity);
        Mockito.when(buscarPagamentosCartaoUseCase.execute(any(PagamentoDomainEntity.class)))
                .thenReturn(pagamentos);

        mockMvc.perform(get("/api/pagamentos/cliente/{cpf}", "12345678900")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].valor").value(pagamentoDomainEntity.getValor().doubleValue()))
                .andExpect(jsonPath("$[0].descricao").value(pagamentoDomainEntity.getDescricao()));
    }
}
