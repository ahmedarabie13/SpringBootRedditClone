package com.arabie.redditclone.domain.dtos;

import lombok.*;
import org.springframework.data.annotation.ReadOnlyProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
