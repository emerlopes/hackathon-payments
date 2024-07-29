package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class MetodoBuscarCartoesPorClienteException extends RuntimeException {
    public MetodoBuscarCartoesPorClienteException(
            final String message
    ) {
        super(message);
    }
}
