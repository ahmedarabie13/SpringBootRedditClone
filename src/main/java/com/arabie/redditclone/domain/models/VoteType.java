package com.arabie.redditclone.domain.models;

public enum VoteType {
    UP_VOTE(1), DOWN_VOTE(-1),
    ;

    VoteType(int direction) {
    }
}
