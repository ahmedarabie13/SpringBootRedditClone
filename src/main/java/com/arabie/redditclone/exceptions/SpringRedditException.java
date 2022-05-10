package com.arabie.redditclone.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SpringRedditException extends RuntimeException {
    private final HttpStatus httpStatus;

    public SpringRedditException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
