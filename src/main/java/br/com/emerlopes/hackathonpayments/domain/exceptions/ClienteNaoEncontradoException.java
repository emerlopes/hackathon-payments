package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
