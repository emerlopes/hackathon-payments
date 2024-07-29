package br.com.emerlopes.payments.domain.exceptions;

public class ErroDeNegocioException extends RuntimeException {
    public ErroDeNegocioException(
            final String message
    ) {
        super(message);
    }
}
