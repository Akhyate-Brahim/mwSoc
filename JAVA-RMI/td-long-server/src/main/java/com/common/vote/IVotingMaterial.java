package com.common.vote;

import com.common.exceptions.AlreadyUsedOTPException;
import com.common.exceptions.IncorrectScoreException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IVotingMaterial extends Remote {
    String getOTP() throws RemoteException;

    public void castVote(Map<Integer, Integer> candidateScores, String otp)
            throws RemoteException, IncorrectScoreException;

    boolean validateOTP(String otp) throws RemoteException, AlreadyUsedOTPException;
}
