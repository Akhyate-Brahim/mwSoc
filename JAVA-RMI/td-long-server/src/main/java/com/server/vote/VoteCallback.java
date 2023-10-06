package com.server.vote;

public interface VoteCallback {
    void onVoteCast(int studentNumber, String info);
}
