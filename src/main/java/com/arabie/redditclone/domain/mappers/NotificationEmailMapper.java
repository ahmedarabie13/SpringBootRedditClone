package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.messaging.NotificationEmailDto;
import com.arabie.redditclone.domain.models.NotificationEmail;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface NotificationEmailMapper {

    NotificationEmailDto toDto(NotificationEmail email);
}
