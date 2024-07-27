package br.com.emerlopes.hackathonpayments.domain.exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
