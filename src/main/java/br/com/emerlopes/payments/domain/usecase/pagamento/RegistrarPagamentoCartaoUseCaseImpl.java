package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.application.shared.CpfUtils;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RegistrarPagamentoCartaoUseCaseImpl implements RegistrarPagamentoCartaoUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(RegistrarPagamentoCartaoUseCaseImpl.class);
    private final PagamentoDomainRepository pagamentoDomainRepository;
    private final CartaoDomainRepository cartaoDomainRepository;

    public RegistrarPagamentoCartaoUseCaseImpl(
            final PagamentoDomainRepository pagamentoDomainRepository,
            final CartaoDomainRepository cartaoDomainRepository
    ) {
        this.pagamentoDomainRepository = pagamentoDomainRepository;
        this.cartaoDomainRepository = cartaoDomainRepository;
    }

    @Override
    public PagamentoDomainEntity execute(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {

        final String cpf = pagamentoDomainEntity.getCpf();
        final String cpfMascarado = CpfUtils.mascararCpf(cpf);
        final BigDecimal valorPagamento = pagamentoDomainEntity.getValor();

        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity.builder()
                .cpf(pagamentoDomainEntity.getCpf())
                .build();

        final var cartoesCliente = cartaoDomainRepository.buscarCartoesClientePorCpf(cartaoDomainEntity);

        if (cartoesCliente.isEmpty()) {
            log.error("Nenhum cartao encontrado para o CPF: {}", cpfMascarado);
            throw new BusinessExceptions("Cartao nao encontrado para o CPF: " + cpfMascarado);
        }

        if (valorPagamento.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Valor de pagamento invalido: {}", valorPagamento);
            throw new BusinessExceptions("Valor de pagamento invalido: " + valorPagamento);
        }

        if (pagamentoDomainEntity.getValor().compareTo(cartoesCliente.get(0).getLimite()) > 0) {
            log.error("Saldo insuficiente para pagamento: {}", valorPagamento);
            throw new BusinessExceptions("Saldo insuficiente para pagamento: " + valorPagamento);
        }

        final PagamentoDomainEntity pagamentoRegistrado = pagamentoDomainRepository.registrarPagamentoCartao(
                pagamentoDomainEntity
        );

        // TODO: Atulalizar valor limite do cartao

        return pagamentoRegistrado;

    }

}
