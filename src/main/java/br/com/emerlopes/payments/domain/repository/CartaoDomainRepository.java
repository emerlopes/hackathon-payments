package br.com.emerlopes.payments.domain.repository;

import br.com.emerlopes.payments.domain.entity.CartaoDomainEntity;

import java.util.List;
import java.util.Optional;

public interface CartaoDomainRepository {
    CartaoDomainEntity gerarCartao(final CartaoDomainEntity cartaoDomainEntity);

    boolean jaPossuiDoisCartoes(final CartaoDomainEntity cartaoDomainEntity);

    boolean jaPossuiCartaoCadastrado(final CartaoDomainEntity cartaoDomainEntity);

    CartaoDomainEntity buscarCartaoPorId(final CartaoDomainEntity cartaoDomainEntity);

    List<CartaoDomainEntity> buscarCartoesClientePorId(final CartaoDomainEntity cartaoDomainEntity);

    List<CartaoDomainEntity> buscarCartoesClientePorCpf(final CartaoDomainEntity cartaoDomainEntity);

    Optional<CartaoDomainEntity> buscarCartaoClientePorNumero(final CartaoDomainEntity cartaoDomainEntity);

    Void atualizarParaNovoLimiteCartaoPorNumero(final CartaoDomainEntity cartaoDomainEntity);
}
