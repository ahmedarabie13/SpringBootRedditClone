package com.arabie.redditclone.exceptions.handlers;

import com.arabie.redditclone.exceptions.SpringRedditException;
import com.arabie.redditclone.exceptions.handlers.dto.ProblemDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice(basePackages = {"com.arabie.redditclone.resources.controllers"})
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(SpringRedditException.class)
    public ResponseEntity<ProblemDto> handleSpringRedditException(SpringRedditException exception){
        var problemDto = ProblemDto.builder()
                .errorMessage(exception.getMessage())
                .status(exception.getHttpStatus())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(problemDto, exception.getHttpStatus());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDto> handleConstraintViolationException(ConstraintViolationException exception){
        var problemDto = ProblemDto.builder()
                .errorMessage(exception.getConstraintViolations().stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", ")))
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(problemDto, HttpStatus.BAD_REQUEST);
    }

}
