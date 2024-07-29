package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;

import java.util.List;
import java.util.stream.Collectors;

public class Converter {
    public static ClienteEntity converterParaClienteEntity(CartaoDomainEntity cartaoDomainEntity) {
        ClienteDomainEntity clienteDomainEntity = cartaoDomainEntity.getCliente();

        List<CartaoEntity> cartaoEntities = clienteDomainEntity.getCartoes().stream()
                .map(cartao -> CartaoEntity.builder()
                        .id(cartao.getId())
                        .cpf(cartao.getCpf())
                        .numero(cartao.getNumero())
                        .limite(cartao.getLimite())
                        .dataValidade(cartao.getDataValidade())
                        .cvv(cartao.getCvv())
                        .pagamentos(cartao.getPagamentos().stream()
                                .map(pagamento -> PagamentoEntity.builder()
                                        .id(pagamento.getId())
                                        .cpf(pagamento.getCpf())
                                        .numero(pagamento.getNumero())
                                        .dataValidade(pagamento.getDataValidade())
                                        .cvv(pagamento.getCvv())
                                        .valor(pagamento.getValor())
                                        .dataPagamento(pagamento.getDataPagamento())
                                        .cartao(null)
                                        .build())
                                .collect(Collectors.toList()))
                        .cliente(null)
                        .build())
                .collect(Collectors.toList());

        ClienteEntity clienteEntity = ClienteEntity.builder()
                .id(clienteDomainEntity.getId())
                .nome(clienteDomainEntity.getNome())
                .email(clienteDomainEntity.getEmail())
                .telefone(clienteDomainEntity.getTelefone())
                .rua(clienteDomainEntity.getRua())
                .cidade(clienteDomainEntity.getCidade())
                .estado(clienteDomainEntity.getEstado())
                .cep(clienteDomainEntity.getCep())
                .pais(clienteDomainEntity.getPais())
                .cartoes(cartaoEntities)
                .build();

        for (CartaoEntity cartaoEntity : cartaoEntities) {
            cartaoEntity.setCliente(clienteEntity);
            for (PagamentoEntity pagamentoEntity : cartaoEntity.getPagamentos()) {
                pagamentoEntity.setCartao(cartaoEntity);
            }
        }

        return clienteEntity;
    }
}
