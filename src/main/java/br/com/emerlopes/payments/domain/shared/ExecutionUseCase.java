package br.com.emerlopes.payments.domain.shared;

public interface ExecutionUseCase<T, J> {
    T execute(J domainObject);
}
