package br.com.emerlopes.payments.domain.exceptions;

public class SaldoBusinessExceptions extends RuntimeException {
    public SaldoBusinessExceptions(
            final String message
    ) {
        super(message);
    }
}
