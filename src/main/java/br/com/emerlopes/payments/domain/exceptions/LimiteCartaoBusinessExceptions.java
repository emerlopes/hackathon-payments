package br.com.emerlopes.payments.domain.exceptions;

public class LimiteCartaoBusinessExceptions extends RuntimeException {
    public LimiteCartaoBusinessExceptions(
            final String message
    ) {
        super(message);
    }
}
