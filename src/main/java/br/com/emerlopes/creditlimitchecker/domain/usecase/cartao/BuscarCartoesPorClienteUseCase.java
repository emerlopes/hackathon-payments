package br.com.emerlopes.creditlimitchecker.domain.usecase.cartao;

import br.com.emerlopes.creditlimitchecker.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.shared.ExecutionUseCase;

import java.util.List;

public interface BuscarCartoesPorClienteUseCase extends ExecutionUseCase<List<CartaoDomainEntity>, CartaoDomainEntity> {
}
