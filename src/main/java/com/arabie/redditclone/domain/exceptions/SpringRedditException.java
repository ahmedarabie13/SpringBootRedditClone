package com.arabie.redditclone.domain.exceptions;

import org.springframework.http.HttpStatus;

public class SpringRedditException extends RuntimeException {
    private final HttpStatus httpStatus;
    public SpringRedditException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
