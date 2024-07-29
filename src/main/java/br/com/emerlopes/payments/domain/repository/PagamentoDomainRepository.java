package br.com.emerlopes.payments.domain.repository;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;

import java.util.List;

public interface PagamentoDomainRepository {
    PagamentoDomainEntity save(final PagamentoDomainEntity pagamentoDomainEntity);

    List<PagamentoDomainEntity> findByCpf(final PagamentoDomainEntity pagamentoDomainEntity);
}
