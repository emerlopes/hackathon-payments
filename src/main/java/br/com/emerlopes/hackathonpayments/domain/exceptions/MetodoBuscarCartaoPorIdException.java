package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class MetodoBuscarCartaoPorIdException extends RuntimeException {
    public MetodoBuscarCartaoPorIdException(
            final String message
    ) {
        super(message);
    }
}
