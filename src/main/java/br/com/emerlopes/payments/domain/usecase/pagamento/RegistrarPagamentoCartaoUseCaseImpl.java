package br.com.emerlopes.payments.domain.usecase.pagamento;

import br.com.emerlopes.payments.application.shared.CpfUtils;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.SaldoBusinessExceptions;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.repository.PagamentoDomainRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

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
        final String numeroCartao = pagamentoDomainEntity.getNumero();

        final CartaoDomainEntity cartaoDomainEntity = CartaoDomainEntity.builder()
                .numero(numeroCartao)
                .build();

        final Optional<CartaoDomainEntity> cartao = cartaoDomainRepository.buscarCartaoClientePorNumero(cartaoDomainEntity);

        if (cartao.isEmpty()) {
            log.error("Nenhum cartao encontrado para o CPF: {}", cpfMascarado);
            throw new BusinessExceptions("Cartao nao encontrado para o CPF: " + cpfMascarado);
        }

        if (valorPagamento.compareTo(BigDecimal.ZERO) <= 0) {
            log.error("Valor de pagamento nao pode ser menor ou igual a zero: {}", valorPagamento);
            throw new BusinessExceptions("Valor de pagamento nao pode ser menor ou igual a zero: " + valorPagamento);
        }

        final LocalDate dataValidadePagemento = pagamentoDomainEntity.getDataValidade();
        final LocalDate dataValidadeCartao = cartao.get().getDataValidade();

        if (!dataValidadePagemento.equals(dataValidadeCartao)) {
            log.error("Data de pagamento invalida: {}", dataValidadePagemento);
            throw new BusinessExceptions("Data de pagamento invalida: " + dataValidadePagemento);
        }

        if (LocalDate.now().isAfter(dataValidadeCartao)) {
            log.error("Cartao vencido: {}", dataValidadeCartao);
            throw new BusinessExceptions("Cartao vencido: " + dataValidadeCartao);
        }

        final String cvvCartao = cartao.get().getCvv();
        if (!cvvCartao.equals(pagamentoDomainEntity.getCvv())) {
            log.error("CVV invalido: {}", pagamentoDomainEntity.getCvv());
            throw new BusinessExceptions("CVV invalido: " + pagamentoDomainEntity.getCvv());
        }

        if (pagamentoDomainEntity.getValor().compareTo(cartao.get().getLimite()) > 0) {
            log.error("Saldo insuficiente para pagamento: {}", valorPagamento);
            throw new SaldoBusinessExceptions("Saldo insuficiente para pagamento: " + valorPagamento);
        }

        final PagamentoDomainEntity pagamentoRegistrado = pagamentoDomainRepository.registrarPagamentoCartao(
                pagamentoDomainEntity
        );

        final BigDecimal valorLimiteAtual = cartao.get().getLimite();
        final BigDecimal novoLimite = valorLimiteAtual.subtract(valorPagamento);

        cartaoDomainRepository.atualizarParaNovoLimiteCartaoPorNumero(
                CartaoDomainEntity.builder()
                        .numero(numeroCartao)
                        .limite(novoLimite)
                        .build()
        );

        return pagamentoRegistrado;

    }

}
