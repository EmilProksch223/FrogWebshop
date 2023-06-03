package at.technikumwien.webshop.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity could not be found")
public class EntityNotFoundException extends RuntimeException {
    // noop
}
