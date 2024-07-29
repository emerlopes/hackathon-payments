package br.com.emerlopes.payments.domain.exceptions;

public class BusinessExceptions extends RuntimeException {
    public BusinessExceptions(
            final String message
    ) {
        super(message);
    }
}
