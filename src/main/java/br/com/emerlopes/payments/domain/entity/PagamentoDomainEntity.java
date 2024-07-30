package br.com.emerlopes.payments.domain.entity;

import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PagamentoDomainEntity {
    private UUID id;
    private String cpf;
    private String numero;
    private LocalDate dataValidade;
    private String cvv;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;
    private CartaoEntity cartao;
}
