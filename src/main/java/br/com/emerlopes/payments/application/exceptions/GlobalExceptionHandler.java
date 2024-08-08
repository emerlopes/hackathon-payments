package br.com.emerlopes.payments.application.exceptions;

import br.com.emerlopes.payments.application.shared.CustomErrorResponse;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.CartaoBusinessExceptions;
import br.com.emerlopes.payments.domain.exceptions.SaldoBusinessExceptions;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<String> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> message.append(violation.getMessage()).append("; "));
        return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(
            final InvalidTokenException ex
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Invalid token used in the request"
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessExceptions.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomErrorResponse> handleBusinessExceptions(
            final Exception ex,
            final WebRequest request
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SaldoBusinessExceptions.class)
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    public ResponseEntity<CustomErrorResponse> handleSaldoBusinessExceptions(
            final SaldoBusinessExceptions ex,
            final WebRequest request
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                "Saldo limite excedido",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(CartaoBusinessExceptions.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<CustomErrorResponse> handleCartaoBusinessExceptions(
            final CartaoBusinessExceptions ex,
            final WebRequest request
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                "Número maximo de cartões atingido",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }

}
