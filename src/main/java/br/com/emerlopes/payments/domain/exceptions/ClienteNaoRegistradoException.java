package br.com.emerlopes.payments.domain.exceptions;

public class ClienteNaoRegistradoException extends RuntimeException {
    public ClienteNaoRegistradoException(
            final String message
    ) {
        super(message);
    }
}
