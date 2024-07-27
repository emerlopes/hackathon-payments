package br.com.emerlopes.hackathonpayments.repository;

import br.com.emerlopes.hackathonpayments.domain.entity.CartaoDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.exceptions.MetodoBuscarCartaoPorIdException;
import br.com.emerlopes.hackathonpayments.domain.exceptions.MetodoBuscarCartoesPorClienteException;
import br.com.emerlopes.hackathonpayments.domain.exceptions.CartaoNaoEncontradoException;
import br.com.emerlopes.hackathonpayments.domain.exceptions.ErroGeracaoCartaoException;
import br.com.emerlopes.hackathonpayments.domain.repository.CartaoDomainRepository;
import br.com.emerlopes.hackathonpayments.infrastructure.database.entity.CartaoEntity;
import br.com.emerlopes.hackathonpayments.infrastructure.database.repository.CartaoRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

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
                    .numero(cartaoDomainEntity.getNumero())
                    .limite(cartaoDomainEntity.getLimite())
                    .dataValidade(cartaoDomainEntity.getDataValidade())
                    .cvv(cartaoDomainEntity.getCvv())
                    .build();

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
    public CartaoDomainEntity BuscarCartaoPorId(
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
                            .build())
                    .toList();
        } catch (final Throwable throwable) {
            log.error("Erro ao buscar cartoes: {}", throwable.getMessage());
            throw new MetodoBuscarCartoesPorClienteException("Erro nao esperado ao buscar cartoes por cliente");
        }
    }
}
