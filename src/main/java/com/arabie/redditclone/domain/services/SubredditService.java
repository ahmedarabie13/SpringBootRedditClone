package com.arabie.redditclone.domain.services;

import com.arabie.redditclone.domain.models.Subreddit;
import com.arabie.redditclone.domain.repos.SubredditRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
