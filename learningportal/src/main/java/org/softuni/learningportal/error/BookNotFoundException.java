package org.softuni.learningportal.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Няма такава книга!")
public class BookNotFoundException extends RuntimeException {

    private int statusCode;

    public BookNotFoundException() {
        this.statusCode = 404;
    }

    public BookNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
