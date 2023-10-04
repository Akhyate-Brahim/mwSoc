package com.common.login;

import com.common.vote.IVotingMaterial;
import com.common.exceptions.BadCredentialsException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {
    public IVotingMaterial requestVotingMaterial(IClient client) throws RemoteException, BadCredentialsException;
}
