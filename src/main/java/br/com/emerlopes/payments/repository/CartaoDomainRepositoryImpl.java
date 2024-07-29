package br.com.emerlopes.payments.repository;

import br.com.emerlopes.payments.application.exceptions.ResourceNotFoundException;
import br.com.emerlopes.payments.application.shared.CartaoUtils;
import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.payments.domain.exceptions.*;
import br.com.emerlopes.payments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import br.com.emerlopes.payments.infrastructure.database.entity.ClienteEntity;
import br.com.emerlopes.payments.infrastructure.database.repository.CartaoRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoDomainRepositoryImpl implements CartaoDomainRepository {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(CartaoDomainRepositoryImpl.class);

    private final CartaoRepository cartaoRepository;

    public CartaoDomainRepositoryImpl(
            final CartaoRepository cartaoRepository
    ) {
        this.cartaoRepository = cartaoRepository;
    }

    @Override
    public CartaoDomainEntity gerarCartao(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        try {
            final CartaoEntity cartaoEntity = CartaoEntity
                    .builder()
                    .cpf(cartaoDomainEntity.getCpf())
                    .numero(cartaoDomainEntity.getNumero())
                    .limite(cartaoDomainEntity.getLimite())
                    .dataValidade(cartaoDomainEntity.getDataValidade())
                    .cvv(cartaoDomainEntity.getCvv())
                    .build();

            final ClienteEntity clienteEntity = Converter.converterParaClienteEntity(cartaoDomainEntity);

            cartaoEntity.setCliente(clienteEntity);
            cartaoEntity.setPagamentos(List.of());

            final var cartaoGerado = cartaoRepository.save(cartaoEntity);

            log.info("Cartao gerado: {}", cartaoGerado.getId());

            return CartaoDomainEntity.builder()
                    .id(cartaoGerado.getId())
                    .build();
        } catch (final Throwable throwable) {
            log.error("Erro ao salvar cartao: {}", throwable.getMessage());
            throw new ErroGeracaoCartaoException("Erro ao salvar cartao");
        }

    }

    @Override
    public boolean jaPossuiDoisCartoes(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        final String cpf = cartaoDomainEntity.getCpf();

        final Optional<List<CartaoEntity>> cartoesEntity = cartaoRepository.findByCpf(cpf);

        final List<CartaoEntity> cartoes = isCartoesEmpty(cartoesEntity);

        log.info("Quantidade de cartoes encontrados para o cliente: {}", cartoes.size());

        return cartoes.size() >= 2;

    }

    @Override
    public CartaoDomainEntity buscarCartaoPorId(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        try {
            final var cartaoId = cartaoDomainEntity.getId();

            final var cartao = cartaoRepository.findById(cartaoId);

            if (cartao.isEmpty()) {
                log.info("Cartao nao encontrado: {}", cartaoId);
                throw new CartaoNaoEncontradoException("Cartao nao encontrado: " + cartaoId);
            }

            log.info("Cartao encontrado: {}", cartaoId);

            return CartaoDomainEntity.builder()
                    .id(cartao.get().getId())
                    .build();
        } catch (final Throwable throwable) {
            log.error("Erro ao buscar cartao: {}", throwable.getMessage());
            throw new MetodoBuscarCartaoPorIdException("Erro nao esperado ao buscar cartao por id");
        }
    }

    @Override
    public List<CartaoDomainEntity> BuscarCartoesPorCliente(
            final CartaoDomainEntity cartaoDomainEntity
    ) {
        try {
            final var clienteId = cartaoDomainEntity.getIdCliente();

            final var cartoes = cartaoRepository.findByClienteId(clienteId);

            if (cartoes.isEmpty()) {
                log.info("Nenhum cartao encontrado para o cliente: {}", clienteId);
                throw new CartaoNaoEncontradoException("Nenhum cartao encontrado para o cliente: " + clienteId);
            }

            log.info("Cartoe(s) encontrado(s) para o cliente: {}", clienteId);

            return cartoes.get().stream()
                    .map(cartao -> CartaoDomainEntity.builder()
                            .id(cartao.getId())
                            .numero(CartaoUtils.mascararCartaoCredito(cartao.getNumero()))
                            .build())
                    .toList();
        } catch (final Throwable throwable) {
            log.error("Erro ao buscar cartoes: {}", throwable.getMessage());
            throw new MetodoBuscarCartoesPorClienteException("Erro nao esperado ao buscar cartoes por cliente");
        }
    }

    private List<CartaoEntity> isCartoesEmpty(Optional<List<CartaoEntity>> cartao) {
        if (cartao.isEmpty()) {
            log.info("Nenhum cartao encontrado para o cliente");
            throw new ResourceNotFoundException("Nenhum cartao encontrado para o cliente");
        }
        return cartao.get();
    }

}
