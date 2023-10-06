package com.common.login;

import com.common.vote.IVoteManager;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILogin extends Remote {

    public IVoteManager requestVotingMaterial(IClient client) throws RemoteException;
}
