package com.common.vote;

import com.common.exceptions.IncorrectOTPException;
import com.common.exceptions.IncorrectScoreException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IVoteManager extends Remote {
    String getOTP(int studentNumber) throws RemoteException, IncorrectOTPException;
    void castVote(int studentNumber, Map<Integer, Integer> candidateScores, String otp)
            throws RemoteException, IncorrectOTPException, IncorrectScoreException;

    boolean hasAlreadyVoted(int studentNumber) throws RemoteException;

    boolean validateAndRemovePreviousVote(int studentNumber, String otp) throws RemoteException, IncorrectOTPException;

    void generateOTP(int studentNumber) throws RemoteException;

    boolean validateOTP(int studentNumber, String otp) throws RemoteException;

    String getVotingResults() throws RemoteException;
}
