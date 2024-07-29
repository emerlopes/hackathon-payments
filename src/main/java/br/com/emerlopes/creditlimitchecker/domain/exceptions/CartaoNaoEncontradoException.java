package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
