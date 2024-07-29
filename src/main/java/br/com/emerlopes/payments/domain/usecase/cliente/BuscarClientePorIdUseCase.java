package br.com.emerlopes.payments.domain.usecase.cliente;

import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.shared.ExecutionUseCase;

public interface BuscarClientePorIdUseCase extends ExecutionUseCase<ClienteDomainEntity, ClienteDomainEntity> {
}
