package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class ClienteNaoRegistradoException extends RuntimeException {
    public ClienteNaoRegistradoException(
            final String message
    ) {
        super(message);
    }
}
