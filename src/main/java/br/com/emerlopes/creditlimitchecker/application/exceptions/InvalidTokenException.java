package br.com.emerlopes.creditlimitchecker.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("The provided token is invalid or has expired. Please authenticate again.");
    }
}
