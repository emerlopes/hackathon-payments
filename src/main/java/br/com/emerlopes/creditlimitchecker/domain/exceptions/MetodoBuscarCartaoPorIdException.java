package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class MetodoBuscarCartaoPorIdException extends RuntimeException {
    public MetodoBuscarCartaoPorIdException(
            final String message
    ) {
        super(message);
    }
}
