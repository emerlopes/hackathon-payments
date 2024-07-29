package br.com.emerlopes.creditlimitchecker.domain.exceptions;

public class ClienteNaoRegistradoException extends RuntimeException {
    public ClienteNaoRegistradoException(
            final String message
    ) {
        super(message);
    }
}
