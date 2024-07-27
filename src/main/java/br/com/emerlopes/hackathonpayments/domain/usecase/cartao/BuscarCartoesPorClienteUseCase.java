package br.com.emerlopes.hackathonpayments.domain.usecase.cartao;

import br.com.emerlopes.hackathonpayments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.shared.ExecutionUseCase;

import java.util.List;

public interface BuscarCartoesPorClienteUseCase extends ExecutionUseCase<List<CartaoDomainEntity>, CartaoDomainEntity> {
}
