package com.arabie.redditclone.resources.controllers;

import com.arabie.redditclone.domain.dtos.PostDto;
import com.arabie.redditclone.domain.dtos.paging.PageDto;
import com.arabie.redditclone.domain.mappers.PostMapper;
import com.arabie.redditclone.domain.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostMapper mapper;
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        var createdPost = postService.createPost(postDto);
        return ResponseEntity
                .created(URI.create("api/posts/"+createdPost.getId()))
                .body(mapper.toDto(createdPost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("id") long id){
        return ResponseEntity.ok(mapper.toDto(postService.getPost(id)));
    }

    @GetMapping
    public ResponseEntity<PageDto<PostDto>> getAllPosts(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        var pageRequest = PageRequest.of(pageNumber,pageSize);
        var postPageDto = mapper.toPageDto(postService.getAllPosts(pageRequest));
        return ResponseEntity.ok(postPageDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        postDto.setId(id);
        var updatedPost = mapper.toDto(postService.updatePost(id, postDto));
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
