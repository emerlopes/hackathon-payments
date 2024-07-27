package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class MetodoBuscarCartoesPorClienteException extends RuntimeException {
    public MetodoBuscarCartoesPorClienteException(
            final String message
    ) {
        super(message);
    }
}
