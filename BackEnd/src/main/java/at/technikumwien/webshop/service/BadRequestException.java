package at.technikumwien.webshop.service;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(code = BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    // noop
}
