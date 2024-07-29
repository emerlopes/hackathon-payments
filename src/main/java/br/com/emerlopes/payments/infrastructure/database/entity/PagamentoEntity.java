package br.com.emerlopes.payments.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cpf;
    private String numero;
    private String dataValidade;
    private String cvv;
    private BigDecimal valor;
    private LocalDateTime dataPagamento;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private CartaoEntity cartao;
}
