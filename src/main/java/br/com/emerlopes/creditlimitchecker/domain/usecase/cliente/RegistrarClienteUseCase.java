package br.com.emerlopes.creditlimitchecker.domain.usecase.cliente;

import br.com.emerlopes.creditlimitchecker.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.shared.ExecutionUseCase;

public interface RegistrarClienteUseCase extends ExecutionUseCase<ClienteDomainEntity, ClienteDomainEntity> {
}
