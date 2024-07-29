package br.com.emerlopes.payments.domain.entity;

import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
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
    private String numero;
    private BigDecimal limite;
    private LocalDate dataValidade;
    private String cvv;
    private ClienteEntity cliente;
    private List<PagamentoEntity> pagamentos;
}
