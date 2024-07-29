package br.com.emerlopes.payments.domain.usecase.cartao.impl;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.domain.usecase.cartao.BuscarCartoesPorClienteUseCase;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarCartoesPorClienteUseCaseImpl implements BuscarCartoesPorClienteUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(BuscarCartoesPorClienteUseCaseImpl.class);
    private final CartaoDomainRepository cartaoDomainRepository;

    public BuscarCartoesPorClienteUseCaseImpl(
            final CartaoDomainRepository cartaoDomainRepository
    ) {
        this.cartaoDomainRepository = cartaoDomainRepository;
    }

    @Override
    public List<CartaoDomainEntity> execute(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        log.info("Buscando cartoes do cliente: {}", cartaoDomainEntity.getIdCliente());
        return cartaoDomainRepository.buscarCartoesPorCliente(cartaoDomainEntity);
    }
}
