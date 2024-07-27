package br.com.emerlopes.hackathonpayments.repository;

import br.com.emerlopes.hackathonpayments.domain.entity.ClienteDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.exceptions.ClienteNaoEncontradoException;
import br.com.emerlopes.hackathonpayments.domain.exceptions.ClienteNaoRegistradoException;
import br.com.emerlopes.hackathonpayments.domain.repository.ClienteDomainRepository;
import br.com.emerlopes.hackathonpayments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.hackathonpayments.infrastructure.database.repository.ClienteRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteDomainRepositoryImpl implements ClienteDomainRepository {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(ClienteDomainRepositoryImpl.class);
    private final ClienteRepository clienteRepository;

    public ClienteDomainRepositoryImpl(
            final ClienteRepository clienteRepository
    ) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDomainEntity registrarCliente(
            final ClienteDomainEntity clienteDomainEntity
    ) {
        try {
            final ClienteEntity clienteEntity = ClienteEntity.builder()
                    .nome(clienteDomainEntity.getNome())
                    .cpf(clienteDomainEntity.getCpf())
                    .email(clienteDomainEntity.getEmail())
                    .telefone(clienteDomainEntity.getTelefone())
                    .rua(clienteDomainEntity.getRua())
                    .cidade(clienteDomainEntity.getCidade())
                    .estado(clienteDomainEntity.getEstado())
                    .cep(clienteDomainEntity.getCep())
                    .pais(clienteDomainEntity.getPais())
                    .cartoes(List.of())
                    .build();

            final ClienteEntity clienteRegistrado = clienteRepository.save(clienteEntity);

            return ClienteDomainEntity.builder()
                    .id(clienteRegistrado.getId())
                    .nome(clienteRegistrado.getNome())
                    .cpf(clienteRegistrado.getCpf())
                    .email(clienteRegistrado.getEmail())
                    .telefone(clienteRegistrado.getTelefone())
                    .rua(clienteRegistrado.getRua())
                    .cidade(clienteRegistrado.getCidade())
                    .estado(clienteRegistrado.getEstado())
                    .cep(clienteRegistrado.getCep())
                    .pais(clienteRegistrado.getPais())
                    .cartoes(List.of())
                    .build();
        } catch (final Throwable throwable) {
            log.error("Erro ao registrar cliente", throwable);
            throw new ClienteNaoRegistradoException("Erro ao registrar cliente");
        }
    }

    @Override
    public ClienteDomainEntity buscarClientePorId(
            final ClienteDomainEntity clienteDomainEntity
    ) {
        return clienteRepository.findById(clienteDomainEntity.getId())
                .map(clienteEntity -> ClienteDomainEntity.builder()
                        .id(clienteEntity.getId())
                        .nome(clienteEntity.getNome())
                        .cpf(clienteEntity.getCpf())
                        .email(clienteEntity.getEmail())
                        .telefone(clienteEntity.getTelefone())
                        .rua(clienteEntity.getRua())
                        .cidade(clienteEntity.getCidade())
                        .estado(clienteEntity.getEstado())
                        .cep(clienteEntity.getCep())
                        .pais(clienteEntity.getPais())
                        .cartoes(List.of())
                        .build()
                )
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente nao encontrado"));
    }

    @Override
    public ClienteDomainEntity buscarClientePorCpf(
            final ClienteDomainEntity clienteDomainEntity
    ) {

        final var clienteEntrado = clienteRepository.findByCpf(clienteDomainEntity.getCpf())
                .map(clienteEntity -> ClienteDomainEntity.builder()
                        .id(clienteEntity.getId())
                        .nome(clienteEntity.getNome())
                        .cpf(clienteEntity.getCpf())
                        .email(clienteEntity.getEmail())
                        .telefone(clienteEntity.getTelefone())
                        .rua(clienteEntity.getRua())
                        .cidade(clienteEntity.getCidade())
                        .estado(clienteEntity.getEstado())
                        .cep(clienteEntity.getCep())
                        .pais(clienteEntity.getPais())
                        .cartoes(List.of())
                        .build()
                );

        return clienteEntrado.orElse(null);

    }

    @Override
    public List<ClienteDomainEntity> buscarClientes(final ClienteDomainEntity clienteDomainEntity) {
        List<ClienteDomainEntity> clientes = clienteRepository.findAll().stream()
                .map(clienteEntity -> ClienteDomainEntity.builder()
                        .id(clienteEntity.getId())
                        .nome(clienteEntity.getNome())
                        .cpf(clienteEntity.getCpf())
                        .email(clienteEntity.getEmail())
                        .telefone(clienteEntity.getTelefone())
                        .rua(clienteEntity.getRua())
                        .cidade(clienteEntity.getCidade())
                        .estado(clienteEntity.getEstado())
                        .cep(clienteEntity.getCep())
                        .pais(clienteEntity.getPais())
                        .cartoes(List.of())
                        .build()
                )
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            throw new ClienteNaoEncontradoException("Nenhum cliente encontrado.");
        }

        return clientes;
    }
}
