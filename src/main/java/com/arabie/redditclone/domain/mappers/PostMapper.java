package com.arabie.redditclone.domain.mappers;

import com.arabie.redditclone.domain.dtos.PostDto;
import com.arabie.redditclone.domain.dtos.SubredditDto;
import com.arabie.redditclone.domain.dtos.paging.PageDto;
import com.arabie.redditclone.domain.models.Post;
import com.arabie.redditclone.domain.models.Subreddit;
import com.arabie.redditclone.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "subreddit", source = "relatedSubreddit")
    @Mapping(target = "id", source = "postDto.id")
    @Mapping(target = "description", source = "postDto.description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Post toEntity(PostDto postDto, User currentUser, Subreddit relatedSubreddit);

    @Mapping(target = "username", source = "post.user.username")
    @Mapping(target = "subredditName", source = "post.subreddit.name")
    PostDto toDto(Post post);

    default PageDto<PostDto> toPageDto(Page<Post> posts) {
        if (posts == null) {
            return null;
        }

        PageDto<PostDto> pageDto = new PageDto<>();
        for (Post post : posts) {
            pageDto.getContent().add(toDto(post));
        }
        pageDto.setPageNumber(posts.getPageable().getPageNumber());
        pageDto.setPageSize(posts.getPageable().getPageSize());
        pageDto.setTotalPages(posts.getTotalPages());
        pageDto.setTotalElements(posts.getTotalElements());

        return pageDto;
    }
}
