package com.arabie.redditclone.resources.controllers;

import com.arabie.redditclone.domain.dtos.SubredditDto;
import com.arabie.redditclone.domain.dtos.paging.PageDto;
import com.arabie.redditclone.domain.mappers.SubredditMapper;
import com.arabie.redditclone.domain.models.Subreddit;
import com.arabie.redditclone.domain.services.SubredditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/subreddits")
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSubreddit(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable("id") Long id) {
        var subredditDto = mapper.toDto(service.getById(id));
        return ResponseEntity.ok(subredditDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubredditDto> updateSubreddit(@PathVariable("id") Long id, @RequestBody SubredditDto subredditDto){
        subredditDto.setId(id);
        var updatedSubredditDto = mapper.toDto(service.update(id,mapper.toEntity(subredditDto)));
        return ResponseEntity.ok(updatedSubredditDto);
    }

    @GetMapping
    public ResponseEntity<PageDto<SubredditDto>> getAllSubreddits(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        var pageRequest = PageRequest.of(pageNumber,pageSize);
        return ResponseEntity.ok(mapper.toPageDto(service.getAllSubreddits(pageRequest)));
    }
}
