package br.com.emerlopes.payments.domain.exceptions;

public class CartaoBusinessExceptions extends RuntimeException {
    public CartaoBusinessExceptions(
            final String message
    ) {
        super(message);
    }
}
