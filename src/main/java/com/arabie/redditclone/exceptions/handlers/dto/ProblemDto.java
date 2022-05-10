package com.arabie.redditclone.exceptions.handlers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemDto {
    private Instant timestamp;
    private HttpStatus status;
    private String errorMessage;
}
