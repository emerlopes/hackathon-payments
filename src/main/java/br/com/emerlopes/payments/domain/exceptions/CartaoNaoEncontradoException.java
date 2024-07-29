package br.com.emerlopes.payments.domain.exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
