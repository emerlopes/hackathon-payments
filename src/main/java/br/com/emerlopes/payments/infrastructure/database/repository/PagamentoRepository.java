package br.com.emerlopes.payments.infrastructure.database.repository;

import br.com.emerlopes.payments.infrastructure.database.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, UUID> {
    Optional<List<PagamentoEntity>> findByCpf(final String cpf);
}
