package br.com.emerlopes.payments.domain.usecase.cartao.impl;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.usecase.cartao.GerarCartaoUseCase;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GerarCartaoUseCaseImpl implements GerarCartaoUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(GerarCartaoUseCaseImpl.class);
    private final CartaoDomainRepository cartaoDomainRepository;

    public GerarCartaoUseCaseImpl(
            final CartaoDomainRepository cartaoDomainRepository
    ) {
        this.cartaoDomainRepository = cartaoDomainRepository;
    }

    @Override
    public CartaoDomainEntity execute(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        log.info(String.format("Criando cartao para o cliente: %s", cartaoDomainEntity.getNumero()));
        return cartaoDomainRepository.gerarCartao(cartaoDomainEntity);
    }
}
