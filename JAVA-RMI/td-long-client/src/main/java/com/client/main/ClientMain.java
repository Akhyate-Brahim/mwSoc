package com.client.main;
import com.client.interaction.Client;
import com.client.io.*;
import com.common.login.*;
import com.common.candidate.Candidate;
import com.common.vote.IVotingMaterial;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            InputService inputService = new InputService();
            OutputService outputService = new OutputService();
            IClient client = new Client(inputService.getStudentNumber(), inputService.getPassword());
            reg.rebind("CLIENT", client);
            ILogin login = (ILogin) reg.lookup("LOGIN");
            IVotingMaterial votingMaterial = login.requestVotingMaterial(client);
            List<Candidate> candidateList = votingMaterial.getCandidateList();
            outputService.printCandidatesList(candidateList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}