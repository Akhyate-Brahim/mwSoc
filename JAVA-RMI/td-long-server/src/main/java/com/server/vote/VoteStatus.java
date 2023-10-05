package com.server.vote;

import com.common.vote.IVoteStatus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VoteStatus extends UnicastRemoteObject implements IVoteStatus {
    private volatile boolean isVotingStarted = false;
    private volatile boolean isVotingEnded = false;

    public VoteStatus() throws RemoteException {
        super();
    }

    @Override
    public void startVote() throws RemoteException {
        isVotingStarted = true;
        isVotingEnded = false;
        System.out.println("Voting has started...");
    }

    @Override
    public void endVote() throws RemoteException {
        isVotingEnded = true;
        isVotingStarted = false;
        System.out.println("Voting has ended.");
    }

    @Override
    public boolean isVotingStarted() throws RemoteException {
        return isVotingStarted;
    }

    @Override
    public boolean isVotingEnded() throws RemoteException {
        return isVotingEnded;
    }
}
