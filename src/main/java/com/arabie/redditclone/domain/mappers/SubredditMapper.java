package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.SubredditDto;
import com.arabie.redditclone.domain.dtos.paging.PageDto;
import com.arabie.redditclone.domain.models.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "posts", ignore = true)
    Subreddit toEntity(SubredditDto dto);

    @Mapping(target = "numberOfPosts", expression = "java(entity.getPosts()==null?0:entity.getPosts().size())")
    SubredditDto toDto(Subreddit entity);

    default PageDto<SubredditDto> toPageDto(Page<Subreddit> subreddits) {
        if (subreddits == null) {
            return null;
        }

        PageDto<SubredditDto> pageDto = new PageDto<>();
        for (Subreddit subreddit : subreddits) {
            pageDto.getContent().add(toDto(subreddit));
        }
        pageDto.setPageNumber(subreddits.getPageable().getPageNumber());
        pageDto.setPageSize(subreddits.getPageable().getPageSize());
        pageDto.setTotalPages(subreddits.getTotalPages());
        pageDto.setTotalElements(subreddits.getTotalElements());

        return pageDto;
    }


}
