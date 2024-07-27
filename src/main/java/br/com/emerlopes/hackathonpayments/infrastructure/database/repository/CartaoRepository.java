package br.com.emerlopes.hackathonpayments.infrastructure.database.repository;

import br.com.emerlopes.hackathonpayments.infrastructure.database.entity.CartaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity, UUID> {
    Optional<List<CartaoEntity>> findByClienteId(final UUID clienteId);
}
