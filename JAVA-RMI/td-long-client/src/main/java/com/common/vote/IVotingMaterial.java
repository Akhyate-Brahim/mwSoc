package com.common.vote;



import com.common.candidate.Candidate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IVotingMaterial extends Remote {
    List<Candidate> getCandidateList() throws RemoteException;
}
