package br.com.emerlopes.payments.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cpf;
    private String numero;
    private BigDecimal limite;
    private LocalDate dataValidade;
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<PagamentoEntity> pagamentos;

}