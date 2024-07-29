package br.com.emerlopes.creditlimitchecker.domain.usecase.cliente;

import br.com.emerlopes.creditlimitchecker.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.creditlimitchecker.domain.repository.ClienteDomainRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class BuscarClientePorIdUseCaseImpl implements BuscarClientePorIdUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(BuscarClientePorIdUseCaseImpl.class);
    private final ClienteDomainRepository clienteDomainRepository;

    public BuscarClientePorIdUseCaseImpl(
            final ClienteDomainRepository clienteDomainRepository
    ) {
        this.clienteDomainRepository = clienteDomainRepository;
    }

    @Override
    public ClienteDomainEntity execute(
            final ClienteDomainEntity clienteDomainEntity
    ) {
        log.info("Buscando cliente");

        final var clienteRegistrado = clienteDomainRepository.buscarClientePorId(clienteDomainEntity);

        log.info("Cliente encontrado com sucesso: {}", clienteRegistrado.getId());

        return clienteRegistrado;
    }
}
