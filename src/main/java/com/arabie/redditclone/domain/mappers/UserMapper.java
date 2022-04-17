package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.UserRegisterDto;
import com.arabie.redditclone.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel ="spring")
public abstract class UserMapper {
    @Autowired
    protected PasswordEncoder passwordEncoder;


    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRegisterDto.getPassword()))")
    public abstract User  registerDtoToEntity(UserRegisterDto userRegisterDto);
}
