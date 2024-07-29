package br.com.emerlopes.payments.domain.exceptions;

public class ErroGeracaoCartaoException extends RuntimeException {
    public ErroGeracaoCartaoException(
            final String message
    ) {
        super(message);
    }
}
