package br.com.emerlopes.payments.domain.repository;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;

import java.util.List;

public interface CartaoDomainRepository {
    CartaoDomainEntity gerarCartao(final CartaoDomainEntity cartaoDomainEntity);

    boolean jaPossuiDoisCartoes(final CartaoDomainEntity cartaoDomainEntity);

    CartaoDomainEntity buscarCartaoPorId(final CartaoDomainEntity cartaoDomainEntity);

    List<CartaoDomainEntity> BuscarCartoesPorCliente(final CartaoDomainEntity cartaoDomainEntity);
}
