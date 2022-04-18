package com.arabie.redditclone.domain.dtos;

import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginDto {
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

}
