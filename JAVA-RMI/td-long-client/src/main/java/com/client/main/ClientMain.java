package com.client.main;

import com.client.interaction.Client;
import com.client.io.InputService;
import com.client.io.OutputService;
import com.common.exceptions.VotingHasEndedException;
import com.common.login.IClient;
import com.common.login.ILogin;
import com.common.vote.ICandidateInfo;
import com.common.vote.IVoteStatus;
import com.common.vote.IVoteManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
            IVoteManager voteManager = login.requestVotingMaterial(client);
            VotingSession votingSession = new VotingSession(client, voteManager, candidateInfo, voteStatus, inputService, outputService);
            votingSession.executeVotingProcess();
            if (voteStatus.isVotingStarted() && !voteStatus.isVotingEnded()){
                if (inputService.askForCurrentResults()){
                    outputService.printMessage(voteManager.getVotingResults());
                }
            }
            if (voteStatus.isVotingEnded()){
                if (inputService.askForFinalResults()){
                    outputService.printMessage(voteManager.getVotingResults());
                }
            }
        } catch (Exception | VotingHasEndedException e) {
            e.printStackTrace();
        }
    }
}
