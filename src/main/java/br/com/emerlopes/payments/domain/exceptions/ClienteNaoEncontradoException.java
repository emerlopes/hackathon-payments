package br.com.emerlopes.payments.domain.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
