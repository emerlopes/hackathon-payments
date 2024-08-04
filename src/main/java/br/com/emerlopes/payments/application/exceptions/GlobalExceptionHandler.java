package br.com.emerlopes.payments.application.exceptions;

import br.com.emerlopes.payments.application.shared.CustomErrorResponse;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<String> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        StringBuilder message = new StringBuilder();
        ex.getConstraintViolations().forEach(violation -> message.append(violation.getMessage()).append("; "));
        return new ResponseEntity<>(message.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidTokenException(
            final InvalidTokenException ex
    ) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BusinessExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleBusinessExceptions(
            final BusinessExceptions ex,
            final WebRequest request
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(SaldoBusinessExceptions.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomErrorResponse> handleSaldoBusinessExceptions(
            final SaldoBusinessExceptions ex,
            final WebRequest request
    ) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.PAYMENT_REQUIRED);
    }

}
