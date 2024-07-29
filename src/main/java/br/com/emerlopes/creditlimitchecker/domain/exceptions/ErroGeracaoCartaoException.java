package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class ErroGeracaoCartaoException extends RuntimeException {
    public ErroGeracaoCartaoException(
            final String message
    ) {
        super(message);
    }
}
