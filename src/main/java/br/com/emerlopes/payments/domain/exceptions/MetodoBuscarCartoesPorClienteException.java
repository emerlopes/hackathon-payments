package br.com.emerlopes.payments.domain.exceptions;

public class MetodoBuscarCartoesPorClienteException extends RuntimeException {
    public MetodoBuscarCartoesPorClienteException(
            final String message
    ) {
        super(message);
    }
}
