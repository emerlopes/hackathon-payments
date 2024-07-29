package br.com.emerlopes.creditlimitchecker.domain.entity;

import br.com.emerlopes.creditlimitchecker.infrastructure.database.entity.CartaoEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ClienteDomainEntity {
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
    private List<CartaoEntity> cartoes;
}
