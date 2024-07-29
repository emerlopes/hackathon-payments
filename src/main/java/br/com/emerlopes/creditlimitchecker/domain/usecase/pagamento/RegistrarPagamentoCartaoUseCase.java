package br.com.emerlopes.creditlimitchecker.domain.usecase.pagamento;

import br.com.emerlopes.creditlimitchecker.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.shared.ExecutionUseCase;

import java.util.List;

public interface RegistrarPagamentoCartaoUseCase extends ExecutionUseCase<List<PagamentoDomainEntity>, PagamentoDomainEntity> {
}
