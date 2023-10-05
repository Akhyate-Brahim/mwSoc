package com.common.vote;

import com.common.exceptions.AlreadyUsedOTPException;
import com.common.exceptions.HasAlreadyVotedException;
import com.common.exceptions.IncorrectScoreException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IVotingMaterial extends Remote {
    String getOTP(int studentNumber) throws RemoteException, AlreadyUsedOTPException;
    void castVote(int studentNumber, Map<Integer, Integer> candidateScores, String otp)
            throws RemoteException, AlreadyUsedOTPException, IncorrectScoreException, HasAlreadyVotedException;
    void generateOTP(int studentNumber) throws RemoteException;
}
