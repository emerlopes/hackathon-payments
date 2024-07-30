package br.com.emerlopes.payments.infrastructure.database.repository;

import br.com.emerlopes.payments.infrastructure.database.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {
    Optional<List<CartaoEntity>> findByClienteId(final UUID clienteId);

    Optional<List<CartaoEntity>> findByCpf(final String cpf);

    Optional<CartaoEntity> findByNumero(final String numero);


}
