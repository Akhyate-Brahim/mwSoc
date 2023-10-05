package com.client.main;

import com.client.interaction.Client;
import com.client.io.*;
import com.common.login.*;
import com.common.vote.ICandidateInfo;
import com.common.vote.IVoteStatus;
import com.common.vote.IVotingMaterial;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

public class ClientMain {

    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            InputService inputService = new InputService();
            OutputService outputService = new OutputService();

            ILogin login = (ILogin) reg.lookup("LOGIN");
            ICandidateInfo candidateInfo = (ICandidateInfo) reg.lookup("ICANDIDATEINFO");
            IVoteStatus voteStatus = (IVoteStatus) reg.lookup("VOTESTATUS");
            outputService.printCandidateList(candidateInfo.getCandidateList());

            IClient client = new Client(inputService.getStudentNumber(), inputService.getPassword());
            reg.rebind("CLIENT", client);

            IVotingMaterial votingMaterial = login.requestVotingMaterial(client);
            votingMaterial.generateOTP(client.getStudentNumber());
            outputService.printOTP(votingMaterial.getOTP(client.getStudentNumber()));
            while (!voteStatus.isVotingStarted()) {
                outputService.printWaitingForVote();
                Thread.sleep(5000);
            }
            Map<Integer, Integer> candidateScores = inputService.getScoreForCandidates(candidateInfo.getCandidateList());
            votingMaterial.castVote(client.getStudentNumber(), candidateScores, votingMaterial.getOTP(client.getStudentNumber()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
