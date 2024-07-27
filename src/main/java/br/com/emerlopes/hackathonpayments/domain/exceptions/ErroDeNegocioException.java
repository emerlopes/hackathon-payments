package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class ErroDeNegocioException extends RuntimeException {
    public ErroDeNegocioException(
            final String message
    ) {
        super(message);
    }
}
