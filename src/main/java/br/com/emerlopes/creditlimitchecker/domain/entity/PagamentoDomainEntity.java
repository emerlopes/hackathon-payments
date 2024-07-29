package br.com.emerlopes.creditlimitchecker.domain.entity;

import br.com.emerlopes.creditlimitchecker.infrastructure.database.entity.CartaoEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PagamentoDomainEntity {
    private UUID id;
    private String cpf;
    private String numero;
    private String dataValidade;
    private String cvv;
    private Double valor;
    private LocalDateTime dataPagamento;
    private CartaoEntity cartao;
}
