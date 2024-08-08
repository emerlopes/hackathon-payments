package br.com.emerlopes.payments.application.entrypoint.rest.cartao;

import br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto.GerarCartaoRequestDTO;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.usecase.cartao.BuscarCartoesPorClienteUseCase;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
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
public class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GerarCartaoUseCase gerarCartaoUseCase;

    @Mock
    BuscarCartoesPorClienteUseCase buscarCartoesPorClienteUseCase;

    @InjectMocks
    private CartaoController cartaoController;

    private GerarCartaoRequestDTO gerarCartaoRequestDTO;
    private CartaoDomainEntity cartaoDomainEntity;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();

        gerarCartaoRequestDTO = GerarCartaoRequestDTO.builder()
                .cpf("12345678900")
                .limite(new BigDecimal("1500.00"))
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .build();

        cartaoDomainEntity = CartaoDomainEntity.builder()
                .id(UUID.randomUUID())
                .cpf("12345678900")
                .limite(new BigDecimal("1500.00"))
                .numero("1234567890123456")
                .dataValidade(LocalDate.of(2025, 12, 31))
                .cvv("123")
                .build();
    }

    @Test
    public void testGerarCartao() throws Exception {
        Mockito.when(gerarCartaoUseCase.execute(any(CartaoDomainEntity.class)))
                .thenReturn(cartaoDomainEntity);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ObjectNode cartaoRequestJsonNode = objectMapper.valueToTree(gerarCartaoRequestDTO);
        cartaoRequestJsonNode.put("data_validade", "12/25"); // Enviando a data no formato MM/yy
        String cartaoRequestJson = objectMapper.writeValueAsString(cartaoRequestJsonNode);

        mockMvc.perform(post("/api/cartao")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(cartaoRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_cartao").value(cartaoDomainEntity.getId().toString()));
    }

    @Test
    public void testBuscarCartoesCliente() throws Exception {
        List<CartaoDomainEntity> cartoes = Collections.singletonList(cartaoDomainEntity);
        Mockito.when(buscarCartoesPorClienteUseCase.execute(any(CartaoDomainEntity.class)))
                .thenReturn(cartoes);

        mockMvc.perform(get("/api/cartao/{idCliente}", UUID.randomUUID().toString())
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id_cartao").value(cartaoDomainEntity.getId().toString()))
                .andExpect(jsonPath("$.[0].numero").value(cartaoDomainEntity.getNumero()));
    }
}