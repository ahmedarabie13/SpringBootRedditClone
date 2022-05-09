package com.arabie.redditclone.resources.controllers;

import com.arabie.redditclone.domain.dtos.SubredditDto;
import com.arabie.redditclone.domain.mappers.SubredditMapper;
import com.arabie.redditclone.domain.services.SubredditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {
    private final SubredditService service;
    private final SubredditMapper mapper;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
        var subreddit = mapper.toEntity(subredditDto);
        var savedSubredditDto = mapper.toDto(service.createSubreddit(subreddit));
        var savedSubredditUri = URI.create("api/subreddit/" + savedSubredditDto.getId());
        return ResponseEntity.created(savedSubredditUri).body(savedSubredditDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubreddit() {
        return ResponseEntity.ok().body("Success");
    }
}
