package com.client.main;
import com.client.interaction.Client;
import com.client.io.*;
import com.common.login.*;
import com.common.candidate.Candidate;
import com.common.vote.ICandidateInfo;
import com.common.vote.IVotingMaterial;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            InputService inputService = new InputService();
            OutputService outputService = new OutputService();

            ILogin login = (ILogin) reg.lookup("LOGIN");
            ICandidateInfo candidateInfo = (ICandidateInfo) reg.lookup("ICANDIDATEINFO");
            outputService.printCandidateList(candidateInfo.getCandidateList());
            IClient client = new Client(inputService.getStudentNumber(), inputService.getPassword());
            reg.rebind("CLIENT", client);
            IVotingMaterial votingMaterial = login.requestVotingMaterial(client);
            String otp = votingMaterial.getOTP();
            outputService.printOTP(otp);
            Map<Integer, Integer> candidateScores = new HashMap<>();
            for (Candidate candidate : candidateInfo.getCandidateList()) {
                int score = inputService.getScoreForCandidate(candidate);  // Assume this method gets the user’s score for the candidate
                candidateScores.put(candidate.getRank(), score);
            }
            votingMaterial.castVote(candidateScores, otp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}