package br.com.emerlopes.creditlimitchecker.domain.shared;

public interface ExecutionUseCase<T, J> {
    T execute(J domainObject);
}
