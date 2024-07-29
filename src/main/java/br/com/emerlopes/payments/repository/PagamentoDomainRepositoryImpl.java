package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.PagamentoRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoDomainRepositoryImpl implements PagamentoDomainRepository {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(PagamentoDomainRepositoryImpl.class);

    private final PagamentoRepository pagamentoRepository;

    public PagamentoDomainRepositoryImpl(
            final PagamentoRepository pagamentoRepository
    ) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public PagamentoDomainEntity registrarPagamentoCartao(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {

        log.info("Registrando pagamento: {}", pagamentoDomainEntity.getValor());

        final PagamentoEntity pagamentoEntity = PagamentoEntity
                .builder()
                .cpf(pagamentoDomainEntity.getCpf())
                .numero(pagamentoDomainEntity.getNumero())
                .dataValidade(pagamentoDomainEntity.getDataValidade())
                .cvv(pagamentoDomainEntity.getCvv())
                .valor(pagamentoDomainEntity.getValor())
                .dataPagamento(LocalDateTime.now())
                .cartao(null)
                .build();

        final PagamentoEntity pagamentoRegistrado = pagamentoRepository.save(pagamentoEntity);

        log.info("Chave pagamento: {}", pagamentoRegistrado.getId());

        return PagamentoDomainEntity.builder()
                .id(pagamentoRegistrado.getId())
                .cpf(pagamentoRegistrado.getCpf())
                .numero(pagamentoRegistrado.getNumero())
                .dataValidade(pagamentoRegistrado.getDataValidade())
                .cvv(pagamentoRegistrado.getCvv())
                .valor(pagamentoRegistrado.getValor())
                .dataPagamento(pagamentoRegistrado.getDataPagamento())
                .cartao(pagamentoRegistrado.getCartao())
                .build();
    }

    @Override
    public List<PagamentoDomainEntity> findByCpf(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {
        return List.of();
    }
}
