package no.nav.oebs.digihot.exception;

import jakarta.validation.ConstraintViolationException;
import no.nav.oebs.digihot.db.entity.ApiError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleHttpClientErrorException_returns401WithCorrectMessage() {
        HttpClientErrorException ex = HttpClientErrorException.create(
                HttpStatus.UNAUTHORIZED, "Unauthorized", null, null, null);

        ResponseEntity<Object> response = handler.handleHttpClientErrorException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.UNAUTHORIZED, apiError.getStatus());
        assertEquals("Feil 401: Ugyldig Aksess token", apiError.getMessage());
        assertFalse(apiError.getErrors().isEmpty());
    }

    @Test
    void handleMethodArgumentTypeMismatch_returns400WithMismatchMessage() {
        MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException(
                "abc", Integer.class, "id", null, new NumberFormatException("For input string: \"abc\""));

        ResponseEntity<Object> response = handler.handleMethodArgumentTypeMismatch(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        assertEquals("Mismatch Type", apiError.getMessage());
        assertFalse(apiError.getErrors().isEmpty());
    }

    @Test
    void handleConstraintViolationException_returns400WithConstraintMessage() {
        ConstraintViolationException ex = new ConstraintViolationException("field must not be empty", Set.of());

        ResponseEntity<?> response = handler.handleConstraintViolationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        assertEquals("Constraint Violation", apiError.getMessage());
        assertFalse(apiError.getErrors().isEmpty());
    }

    @Test
    void handleAll_returns400WithExceptionMessage() {
        Exception ex = new Exception("Something went wrong");

        ResponseEntity<Object> response = handler.handleAll(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
        assertEquals("Something went wrong", apiError.getMessage());
        assertFalse(apiError.getErrors().isEmpty());
    }

    @Test
    void handleDataNotFoundException_withTechnicalPlsqlException_returns200WithEmptyListMessage() {
        TechnicalPlsqlException ex = new TechnicalPlsqlException("No data found");

        ResponseEntity<Object> response = handler.handleDataNotFoundException(ex);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals(HttpStatus.OK, apiError.getStatus());
        assertEquals("[]", apiError.getMessage());
        assertFalse(apiError.getErrors().isEmpty());
    }

    @Test
    void handleDataNotFoundException_withUgyldigInputException_returns200WithEmptyListMessage() {
        UgyldigInputException ex = new UgyldigInputException("Invalid input");

        ResponseEntity<Object> response = handler.handleDataNotFoundException(ex);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        ApiError apiError = (ApiError) response.getBody();
        assertEquals("[]", apiError.getMessage());
    }
}
