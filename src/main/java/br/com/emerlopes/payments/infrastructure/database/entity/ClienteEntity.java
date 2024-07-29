package br.com.emerlopes.payments.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private String rua;
    private String cidade;
    private String estado;
    private String cep;
    private String pais;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<CartaoEntity> cartoes;

}

