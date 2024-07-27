package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class ErroGeracaoCartaoException extends RuntimeException {
    public ErroGeracaoCartaoException(
            final String message
    ) {
        super(message);
    }
}
