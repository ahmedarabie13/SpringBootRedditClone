package com.arabie.redditclone.domain.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {
    private String authenticationToken;
    private String username;
}
