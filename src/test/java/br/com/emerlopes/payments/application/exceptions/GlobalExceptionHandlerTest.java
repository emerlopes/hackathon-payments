package br.com.emerlopes.payments.application.exceptions;

import br.com.emerlopes.payments.application.shared.CustomErrorResponse;
import br.com.emerlopes.payments.domain.exceptions.BusinessExceptions;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    public void testHandleConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Mockito.when(violation.getMessage()).thenReturn("Invalid value");
        Set<ConstraintViolation<?>> violations = Collections.singleton(violation);
        ConstraintViolationException ex = new ConstraintViolationException(violations);

        ResponseEntity<String> response = globalExceptionHandler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Invalid value; ", response.getBody());
    }

    @Test
    public void testHandleInvalidTokenException() {
        InvalidTokenException ex = new InvalidTokenException();

        ResponseEntity<Object> response = globalExceptionHandler.handleInvalidTokenException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Assertions.assertThat(response.getBody());
    }

    @Test
    public void testHandleBusinessExceptions() {
        BusinessExceptions ex = new BusinessExceptions("An unexpected error occurred");
        Mockito.when(webRequest.getDescription(false)).thenReturn("uri=/test");

        ResponseEntity<CustomErrorResponse> response = globalExceptionHandler.handleBusinessExceptions(ex, webRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred", response.getBody().getMessage());
        assertEquals("uri=/test", response.getBody().getDetails());
    }

}
