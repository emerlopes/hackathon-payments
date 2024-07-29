package br.com.emerlopes.creditlimitchecker.domain.usecase.cartao;

import br.com.emerlopes.creditlimitchecker.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.shared.ExecutionUseCase;

public interface GerarCartaoUseCase extends ExecutionUseCase<CartaoDomainEntity, CartaoDomainEntity> {
}
