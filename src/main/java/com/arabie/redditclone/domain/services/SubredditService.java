package com.arabie.redditclone.domain.services;

import com.arabie.redditclone.domain.models.Subreddit;
import com.arabie.redditclone.domain.repos.SubredditRepo;
import com.arabie.redditclone.exceptions.SpringRedditException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepo repo;

    @Transactional
    public Subreddit createSubreddit(Subreddit subreddit) {
        return repo.save(subreddit);
    }

    @Transactional(readOnly = true)
    public Subreddit getById(Long id) {
        var subreddit = repo.findById(id).orElseThrow(() -> {
            throw new SpringRedditException("Subreddit is Not Found", HttpStatus.NOT_FOUND);
        });
        log.info("Number of posts in subreddit: "+subreddit.getPosts().size());
        return subreddit;
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            repo.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new SpringRedditException("No Subreddits found To Be Deleted",HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Subreddit update(Long id, Subreddit subreddit) {
        repo.findById(id).orElseThrow(() -> {
            throw new SpringRedditException("Subreddit is Not Found", HttpStatus.NOT_FOUND);
        });
        return repo.save(subreddit);
    }

    @Transactional(readOnly = true)
    public Page<Subreddit> getAllSubreddits(PageRequest pageRequest) {
        return repo.findAll(pageRequest);
    }

    public Subreddit getByName(String subredditName) {
        return repo.findByName(subredditName).orElseThrow(() -> {
            throw new SpringRedditException("Subreddit is Not Found", HttpStatus.NOT_FOUND);
        });
    }
}
