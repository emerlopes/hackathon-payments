package br.com.emerlopes.hackathonpayments.domain.usecase.pagamento;

import br.com.emerlopes.hackathonpayments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.shared.ExecutionUseCase;

import java.util.List;

public interface RegistrarPagamentoCartaoUseCase extends ExecutionUseCase<List<PagamentoDomainEntity>, PagamentoDomainEntity> {
}
