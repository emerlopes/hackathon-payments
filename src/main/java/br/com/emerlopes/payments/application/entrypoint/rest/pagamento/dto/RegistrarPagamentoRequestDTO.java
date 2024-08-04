package br.com.emerlopes.payments.application.entrypoint.rest.pagamento.dto;

import br.com.emerlopes.payments.application.shared.MonthYearDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrarPagamentoRequestDTO {
    private String cpf;
    private String numero;

    @JsonProperty("data_validade")
    @JsonDeserialize(using = MonthYearDeserializer.class)
    private LocalDate dataValidade;

    private String cvv;
    private BigDecimal valor;
    private String descricao;
}
