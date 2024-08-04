package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BuscarPagamentosCartaoUseCaseImpl implements BuscarPagamentosCartaoUseCase {

    private final PagamentoDomainRepository pagamentoDomainRepository;

    public BuscarPagamentosCartaoUseCaseImpl(
            final PagamentoDomainRepository pagamentoDomainRepository
    ) {
        this.pagamentoDomainRepository = pagamentoDomainRepository;
    }

    @Override
    public List<PagamentoDomainEntity> execute(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {

        final String cpf = pagamentoDomainEntity.getCpf();

        return pagamentoDomainRepository.buscarPagamentosPorCpf(
                PagamentoDomainEntity.builder()
                        .cpf(cpf)
                        .valor(pagamentoDomainEntity.getValor())
                        .descricao(pagamentoDomainEntity.getDescricao())
                        .metodoPagamento(pagamentoDomainEntity.getMetodoPagamento())
                        .statusPagamento(pagamentoDomainEntity.getStatusPagamento())
                        .build()
        );

    }

}
