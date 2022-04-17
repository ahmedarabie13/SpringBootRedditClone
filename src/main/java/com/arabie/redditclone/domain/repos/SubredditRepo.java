package com.arabie.redditclone.domain.repos;

import com.arabie.redditclone.domain.models.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepo extends JpaRepository<Subreddit, Long> {
}
