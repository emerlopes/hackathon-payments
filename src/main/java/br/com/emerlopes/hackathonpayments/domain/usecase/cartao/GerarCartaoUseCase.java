package br.com.emerlopes.hackathonpayments.domain.usecase.cartao;

import br.com.emerlopes.hackathonpayments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.shared.ExecutionUseCase;

public interface GerarCartaoUseCase extends ExecutionUseCase<CartaoDomainEntity, CartaoDomainEntity> {
}
