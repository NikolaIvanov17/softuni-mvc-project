package org.softuni.learningportal.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Книга с това име, вече съществува.")
public class BookNameAlreadyExistsException extends RuntimeException {

    private int statusCode;

    public BookNameAlreadyExistsException() {
        this.statusCode = 409;
    }

    public BookNameAlreadyExistsException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
