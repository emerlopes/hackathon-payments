package br.com.emerlopes.hackathonpayments.domain.shared;

public interface ExecutionUseCase<T, J> {
    T execute(J domainObject);
}
