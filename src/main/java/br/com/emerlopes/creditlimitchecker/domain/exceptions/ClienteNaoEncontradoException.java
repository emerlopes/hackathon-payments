package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(
            final String message
    ) {
        super(message);
    }
}
