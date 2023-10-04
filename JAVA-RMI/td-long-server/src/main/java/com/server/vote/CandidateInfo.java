package com.server.vote;

import com.common.candidate.Candidate;
import com.common.vote.ICandidateInfo;
import com.server.adminApp.AdminApp;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CandidateInfo extends UnicastRemoteObject implements ICandidateInfo {
    AdminApp adminApp;
    public CandidateInfo(AdminApp adminApp) throws RemoteException {
        this.adminApp = adminApp;
    }

    @Override
    public List<Candidate> getCandidateList() throws RemoteException {
        return adminApp.getCandidateList();
    }
}
