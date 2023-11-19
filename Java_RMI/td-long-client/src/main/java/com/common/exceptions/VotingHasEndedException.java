package com.common.exceptions;

public class VotingHasEndedException extends Throwable {
    public VotingHasEndedException() {
        super("Voting has ended ! ");
    }
}
