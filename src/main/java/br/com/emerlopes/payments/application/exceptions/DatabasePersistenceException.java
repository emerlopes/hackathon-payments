package br.com.emerlopes.payments.application.exceptions;

public class DatabasePersistenceException extends RuntimeException {
    public DatabasePersistenceException(
            final String message,
            final Throwable cause
    ) {
        super(message, cause);
    }
}