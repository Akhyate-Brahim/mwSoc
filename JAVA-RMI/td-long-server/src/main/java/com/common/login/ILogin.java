package com.common.login;

import com.common.vote.IVoteManager;
import com.common.exceptions.BadCredentialsException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {
    public IVoteManager requestVotingMaterial(IClient client) throws RemoteException, BadCredentialsException;
}
