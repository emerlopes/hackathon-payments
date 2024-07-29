package br.com.emerlopes.payments.domain.usecase.cartao;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.shared.ExecutionUseCase;

import java.util.List;

public interface BuscarCartoesPorClienteUseCase extends ExecutionUseCase<List<CartaoDomainEntity>, CartaoDomainEntity> {
}
