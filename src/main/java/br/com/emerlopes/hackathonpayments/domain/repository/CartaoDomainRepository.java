package br.com.emerlopes.hackathonpayments.domain.repository;

import br.com.emerlopes.hackathonpayments.domain.entity.CartaoDomainEntity;

import java.util.List;

public interface CartaoDomainRepository {
    CartaoDomainEntity gerarCartao(final CartaoDomainEntity cartaoDomainEntity);

    CartaoDomainEntity BuscarCartaoPorId(final CartaoDomainEntity cartaoDomainEntity);

    List<CartaoDomainEntity> BuscarCartoesPorCliente(final CartaoDomainEntity cartaoDomainEntity);
}
