package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrarPagamentoCartaoUseCaseImpl implements RegistrarPagamentoCartaoUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(RegistrarPagamentoCartaoUseCaseImpl.class);
    private final PagamentoDomainRepository pagamentoDomainRepository;

    public RegistrarPagamentoCartaoUseCaseImpl(
            final PagamentoDomainRepository pagamentoDomainRepository
    ) {
        this.pagamentoDomainRepository = pagamentoDomainRepository;
    }

    @Override
    public List<PagamentoDomainEntity> execute(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {
        return null;
    }
}
