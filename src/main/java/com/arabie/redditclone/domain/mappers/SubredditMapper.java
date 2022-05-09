package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.SubredditDto;
import com.arabie.redditclone.domain.models.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "posts", ignore = true)
    Subreddit toEntity(SubredditDto dto);

    @Mapping(target = "numberOfPosts", expression = "java(entity.getPosts()==null?0:entity.getPosts().size())")
    SubredditDto toDto(Subreddit entity);
}
