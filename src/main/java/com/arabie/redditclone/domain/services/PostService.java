package com.arabie.redditclone.domain.services;

import com.arabie.redditclone.domain.dtos.PostDto;
import com.arabie.redditclone.domain.mappers.PostMapper;
import com.arabie.redditclone.domain.models.Post;
import com.arabie.redditclone.domain.repos.PostRepo;
import com.arabie.redditclone.domain.services.auth.AuthService;
import com.arabie.redditclone.exceptions.SpringRedditException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepo repo;
    private final PostMapper mapper;
    private final SubredditService subredditService;
    private final AuthService authService;


    @Transactional
    public void deletePost(long id) {
        try {
            repo.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new SpringRedditException("No Post found To Be Deleted",HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Post updatePost(long id, PostDto postDto) {
        repo.findById(id).orElseThrow(()->{
            throw new SpringRedditException("Post is Not Found", HttpStatus.NOT_FOUND);
        });
        var subreddit = subredditService.getByName(postDto.getSubredditName());
        var currentUser = authService.getCurrentUser();
        return repo.save(mapper.toEntity(postDto,currentUser,subreddit));
    }

    @Transactional(readOnly = true)
    public Page<Post> getAllPosts(PageRequest pageRequest) {
        return repo.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Post getPost(long id) {
        return repo.findById(id)
                .orElseThrow(()->{
                    throw new SpringRedditException("Post is Not Found", HttpStatus.NOT_FOUND);
                });
    }

    @Transactional
    public Post createPost(PostDto postDto) {
        var subreddit = subredditService.getByName(postDto.getSubredditName());
        var currentUser = authService.getCurrentUser();
        return repo.save(mapper.toEntity(postDto,currentUser,subreddit));
    }
}
