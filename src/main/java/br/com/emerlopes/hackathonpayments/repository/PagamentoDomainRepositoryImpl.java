package br.com.emerlopes.hackathonpayments.repository;

import br.com.emerlopes.hackathonpayments.domain.entity.PagamentoDomainEntity;
import br.com.emerlopes.hackathonpayments.domain.repository.PagamentoDomainRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoDomainRepositoryImpl implements PagamentoDomainRepository {
    @Override
    public PagamentoDomainEntity save(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {
        return null;
    }

    @Override
    public List<PagamentoDomainEntity> findByCpf(
            final PagamentoDomainEntity pagamentoDomainEntity
    ) {
        return List.of();
    }
}
