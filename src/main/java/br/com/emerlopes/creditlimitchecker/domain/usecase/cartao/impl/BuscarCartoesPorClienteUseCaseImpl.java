package br.com.emerlopes.creditlimitchecker.domain.usecase.cartao.impl;

import br.com.emerlopes.creditlimitchecker.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.creditlimitchecker.domain.usecase.cartao.BuscarCartoesPorClienteUseCase;
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
        log.info("Buscando cartoes do cliente: " + cartaoDomainEntity.getIdCliente());
        return cartaoDomainRepository.BuscarCartoesPorCliente(cartaoDomainEntity);
    }
}
