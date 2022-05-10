package com.arabie.redditclone.domain.services;

import com.arabie.redditclone.domain.models.Subreddit;
import com.arabie.redditclone.domain.repos.SubredditRepo;
import com.arabie.redditclone.exceptions.SpringRedditException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubredditService {
    private final SubredditRepo repo;

    @Transactional
    public Subreddit createSubreddit(Subreddit subreddit) {
        return repo.save(subreddit);
    }

    public Subreddit getById(Long id) {
        return repo.findById(id).orElseThrow(() -> {
            throw new SpringRedditException("Subreddit is Not Found", HttpStatus.NOT_FOUND);
        });
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public Subreddit update(Long id, Subreddit subreddit) {
        repo.findById(id).orElseThrow(() -> {
            throw new SpringRedditException("Subreddit is Not Found", HttpStatus.NOT_FOUND);
        });
        return repo.save(subreddit);
    }

    public Page<Subreddit> getAllSubreddits(PageRequest pageRequest) {
        return repo.findAll(pageRequest);
    }
}
