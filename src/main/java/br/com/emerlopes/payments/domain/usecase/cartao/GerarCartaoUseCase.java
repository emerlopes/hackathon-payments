package br.com.emerlopes.payments.domain.usecase.cartao;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.shared.ExecutionUseCase;

public interface GerarCartaoUseCase extends ExecutionUseCase<CartaoDomainEntity, CartaoDomainEntity> {
}
