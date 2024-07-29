package br.com.emerlopes.creditlimitchecker.infrastructure.database.repository;

import br.com.emerlopes.creditlimitchecker.infrastructure.database.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, UUID> {
}
