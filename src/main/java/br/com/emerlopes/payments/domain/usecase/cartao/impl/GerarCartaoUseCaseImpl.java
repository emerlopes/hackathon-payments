package br.com.emerlopes.payments.domain.usecase.cartao.impl;

import br.com.emerlopes.payments.application.shared.CpfUtils;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.CartaoBusinessExceptions;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.repository.ClienteDomainRepository;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GerarCartaoUseCaseImpl implements GerarCartaoUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(GerarCartaoUseCaseImpl.class);
    private final CartaoDomainRepository cartaoDomainRepository;
    private final ClienteDomainRepository clienteDomainRepository;

    public GerarCartaoUseCaseImpl(
            final CartaoDomainRepository cartaoDomainRepository,
            final ClienteDomainRepository clienteDomainRepository
    ) {
        this.cartaoDomainRepository = cartaoDomainRepository;
        this.clienteDomainRepository = clienteDomainRepository;
    }

    @Override
    public CartaoDomainEntity execute(
            final CartaoDomainEntity cartaoDomainEntity
    ) {

        final String cpf = cartaoDomainEntity.getCpf();
        final String cpfMascarado = CpfUtils.mascararCpf(cpf);

        log.info("Verificando quantidade de cartoes permitidos para o cliente: {}", cpfMascarado);

        final boolean jaPossuiDoisCartoes = cartaoDomainRepository.jaPossuiDoisCartoes(
                cartaoDomainEntity
        );

        if (jaPossuiDoisCartoes) {
            log.error("Cliente ja possui dois cartoes");
            throw new CartaoBusinessExceptions("Cliente ja possui dois cartoes");
        }

        final boolean jaPossuiCartaoCadastrado = cartaoDomainRepository.jaPossuiCartaoCadastrado(
                cartaoDomainEntity
        );

        if (jaPossuiCartaoCadastrado) {
            log.error("Cliente ja possui cartao cadastrado");
            throw new BusinessExceptions("Cliente ja possui cartao cadastrado");
        }

        final ClienteDomainEntity clienteDomainEntity = clienteDomainRepository.buscarClientePorCpf(
                ClienteDomainEntity.builder()
                        .cpf(cpf)
                        .build()
        );

        if (clienteDomainEntity == null) {
            log.error("Cliente nao encontrado: {}", cpfMascarado);
            throw new BusinessExceptions("Só é possível registrar um novo cartão para um cliente já existente.");
        }

        cartaoDomainEntity.setCliente(clienteDomainEntity);

        log.info("Criando cartao para o cliente: {}", cpfMascarado);
        return cartaoDomainRepository.gerarCartao(cartaoDomainEntity);
    }
}
