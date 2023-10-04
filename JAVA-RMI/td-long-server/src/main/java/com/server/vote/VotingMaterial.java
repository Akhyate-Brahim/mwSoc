package com.server.vote;

import com.common.vote.IVotingMaterial;
import com.server.adminApp.AdminApp;
import com.common.candidate.Candidate;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class VotingMaterial extends UnicastRemoteObject implements IVotingMaterial {
    AdminApp adminApp;
    public VotingMaterial(AdminApp adminApp) throws RemoteException {
        this.adminApp=adminApp;
    }

    @Override
    public List<Candidate> getCandidateList() throws RemoteException {
        return adminApp.getCandidateList();
    }

}
