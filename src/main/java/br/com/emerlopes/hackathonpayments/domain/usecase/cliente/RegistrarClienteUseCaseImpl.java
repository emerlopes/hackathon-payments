package br.com.emerlopes.hackathonpayments.domain.usecase.cliente;

import br.com.emerlopes.hackathonpayments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.exceptions.ErroDeNegocioException;
import br.com.emerlopes.hackathonpayments.domain.repository.ClienteDomainRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegistrarClienteUseCaseImpl implements RegistrarClienteUseCase {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(RegistrarClienteUseCaseImpl.class);
    private final ClienteDomainRepository clienteDomainRepository;

    public RegistrarClienteUseCaseImpl(
            final ClienteDomainRepository clienteDomainRepository
    ) {
        this.clienteDomainRepository = clienteDomainRepository;
    }

    @Override
    public ClienteDomainEntity execute(
            final ClienteDomainEntity clienteDomainEntity
    ) {

        final ClienteDomainEntity clienteJaRegistrado = clienteDomainRepository.buscarClientePorCpf(clienteDomainEntity);

        if (clienteJaRegistrado != null) {
            log.warn("Cliente já registrado: {}", clienteJaRegistrado.getId());
            throw new ErroDeNegocioException("Cliente já registrado");
        }

        log.info("Registrando cliente");

        final ClienteDomainEntity clienteRegistrado = clienteDomainRepository.registrarCliente(clienteDomainEntity);

        log.info("Cliente registrado com sucesso: {}", clienteRegistrado.getId());

        return clienteRegistrado;
    }
}
