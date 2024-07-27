package br.com.emerlopes.hackathonpayments.domain.usecase.cliente;

import br.com.emerlopes.hackathonpayments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.shared.ExecutionUseCase;

public interface BuscarClientePorIdUseCase extends ExecutionUseCase<ClienteDomainEntity, ClienteDomainEntity> {
}
