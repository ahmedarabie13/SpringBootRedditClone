package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.UserLoginDto;
import com.arabie.redditclone.domain.dtos.UserRegisterDto;
import com.arabie.redditclone.domain.models.User;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegisterDto.getPassword()))")
    public abstract User registerDtoToEntity(UserRegisterDto userRegisterDto);

//    public org.springframework.security.core.userdetails.User toUserDetailsUser(User user) {
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                user.isEnabled(), true, true,
//                true, getAuthorities("USER"));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        return Collections.singletonList(new SimpleGrantedAuthority(role));
//    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthToken(UserLoginDto userLoginDto) {
        log.info(userLoginDto.toString());
//        return new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
        return new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword(), userLoginDto.getAuthorities());
    }
}
