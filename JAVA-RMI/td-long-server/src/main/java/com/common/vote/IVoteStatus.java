package com.common.vote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVoteStatus extends Remote {
    void startVote() throws RemoteException;
    void endVote() throws RemoteException;
    boolean isVotingStarted() throws RemoteException;
    boolean isVotingEnded() throws RemoteException;
}
