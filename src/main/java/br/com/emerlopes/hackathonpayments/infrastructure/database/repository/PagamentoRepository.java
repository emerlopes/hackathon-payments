package br.com.emerlopes.hackathonpayments.infrastructure.database.repository;

import br.com.emerlopes.hackathonpayments.infrastructure.database.entity.PagamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<PagamentoEntity, UUID> {
}
