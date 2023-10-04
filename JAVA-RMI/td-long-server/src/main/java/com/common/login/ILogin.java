package com.common.login;

import com.common.vote.IVotingMaterial;
import com.common.exceptions.BadCredentialsException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {
    boolean authenticate(IClient client) throws RemoteException;
    boolean verifyOTP(int studentNumber, String otp) throws RemoteException;

    public IVotingMaterial requestVotingMaterial(IClient client) throws RemoteException, BadCredentialsException;
}
