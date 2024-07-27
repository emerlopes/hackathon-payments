package br.com.emerlopes.hackathonpayments.infrastructure.database.repository;

import br.com.emerlopes.hackathonpayments.infrastructure.database.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    Optional<ClienteEntity> findByCpf(String cpf);
}
