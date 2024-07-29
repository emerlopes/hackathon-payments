package br.com.emerlopes.payments.domain.repository;

import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;

import java.util.List;

public interface ClienteDomainRepository {
    ClienteDomainEntity registrarCliente(final ClienteDomainEntity clienteDomainEntity);

    ClienteDomainEntity buscarClientePorId(final ClienteDomainEntity clienteDomainEntity);

    ClienteDomainEntity buscarClientePorCpf(final ClienteDomainEntity clienteDomainEntity);

    List<ClienteDomainEntity> buscarClientes(final ClienteDomainEntity clienteDomainEntity);
}
