package br.com.emerlopes.payments.application.entrypoint.rest.cartao.dto;

import br.com.emerlopes.payments.application.shared.MonthYearDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GerarCartaoRequestDTO {
    private String cpf;
    private BigDecimal limite;
    private String numero;

    @JsonProperty("data_validade")
    @JsonDeserialize(using = MonthYearDeserializer.class)
    private LocalDate dataValidade;

    private String cvv;
}
