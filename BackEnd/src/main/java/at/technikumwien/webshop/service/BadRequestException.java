package at.technikumwien.webshop.service;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ResponseStatus(code = BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private final String errorMessage;

    public BadRequestException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @ExceptionHandler
    public static ResponseEntity<String> handleBadRequestException(BadRequestException ex) {
        String errorMessage = ex.getErrorMessage();
        // Zeige die Fehlermeldung an oder handle sie entsprechend
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
