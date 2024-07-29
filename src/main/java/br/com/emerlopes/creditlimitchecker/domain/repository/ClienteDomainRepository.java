package br.com.emerlopes.creditlimitchecker.domain.repository;

import br.com.emerlopes.creditlimitchecker.domain.entity.ClienteDomainEntity;

import java.util.List;

public interface ClienteDomainRepository {
    ClienteDomainEntity registrarCliente(final ClienteDomainEntity clienteDomainEntity);

    ClienteDomainEntity buscarClientePorId(final ClienteDomainEntity clienteDomainEntity);

    ClienteDomainEntity buscarClientePorCpf(final ClienteDomainEntity clienteDomainEntity);

    List<ClienteDomainEntity> buscarClientes(final ClienteDomainEntity clienteDomainEntity);
}
