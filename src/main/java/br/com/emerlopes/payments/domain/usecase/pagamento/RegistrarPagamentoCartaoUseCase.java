package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.shared.ExecutionUseCase;

public interface RegistrarPagamentoCartaoUseCase extends ExecutionUseCase<PagamentoDomainEntity, PagamentoDomainEntity> {
}
