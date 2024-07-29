package br.com.emerlopes.payments.domain.exceptions;

public class MetodoBuscarCartaoPorIdException extends RuntimeException {
    public MetodoBuscarCartaoPorIdException(
            final String message
    ) {
        super(message);
    }
}
