package br.com.emerlopes.payments.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartaoDomainEntity {
    private UUID id;
    private UUID idCliente;
    private String cpf;
    private String numero;
    private BigDecimal limite;
    private LocalDate dataValidade;
    private String cvv;
    private ClienteDomainEntity cliente;
    private List<PagamentoDomainEntity> pagamentos;
}
