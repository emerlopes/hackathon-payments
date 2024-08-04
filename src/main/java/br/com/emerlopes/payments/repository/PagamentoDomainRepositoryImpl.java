package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.application.shared.CpfUtils;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.PagamentoRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
                .descricao(pagamentoDomainEntity.getDescricao())
                .metodoPagamento(pagamentoDomainEntity.getMetodoPagamento())
                .statusPagamento(pagamentoDomainEntity.getStatusPagamento())
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
                .descricao(pagamentoRegistrado.getDescricao())
                .metodoPagamento(pagamentoRegistrado.getMetodoPagamento())
                .statusPagamento(pagamentoRegistrado.getStatusPagamento())
                .dataPagamento(pagamentoRegistrado.getDataPagamento())
                .cartao(pagamentoRegistrado.getCartao())
                .build();
    }

    @Override
    public List<PagamentoDomainEntity> buscarPagamentosPorCpf(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {
        final Optional<List<PagamentoEntity>> pagamentoEntity = pagamentoRepository.findByCpf(pagamentoDomainEntity.getCpf());

        if (pagamentoEntity.isEmpty()) {
            log.error("Nenhum pagamento encontrado para o CPF: {}", CpfUtils.mascararCpf(pagamentoDomainEntity.getCpf()));
            return List.of();
        }

        return pagamentoEntity.get().stream().map(pagamento -> PagamentoDomainEntity.builder()
                .id(pagamento.getId())
                .cpf(pagamento.getCpf())
                .numero(pagamento.getNumero())
                .dataValidade(pagamento.getDataValidade())
                .cvv(pagamento.getCvv())
                .valor(pagamento.getValor())
                .descricao(pagamento.getDescricao())
                .metodoPagamento(pagamento.getMetodoPagamento())
                .statusPagamento(pagamento.getStatusPagamento())
                .dataPagamento(pagamento.getDataPagamento())
                .cartao(pagamento.getCartao())
                .build()).toList();
    }
}
