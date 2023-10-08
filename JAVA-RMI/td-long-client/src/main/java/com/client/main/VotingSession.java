package com.client.main;

import com.client.io.InputService;
import com.client.io.OutputService;
import com.common.exceptions.IncorrectOTPException;
import com.common.exceptions.IncorrectScoreException;
import com.common.exceptions.VotingHasEndedException;
import com.common.login.IClient;
import com.common.vote.ICandidateInfo;
import com.common.vote.IVoteManager;
import com.common.vote.IVoteStatus;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Scanner;

public class VotingSession {

    private final IClient client;
    private final IVoteManager voteManager;
    private final ICandidateInfo candidateInfo;
    private final IVoteStatus voteStatus;
    private final InputService inputService;
    private final OutputService outputService;

    public VotingSession(IClient client, IVoteManager voteManager, ICandidateInfo candidateInfo,
                         IVoteStatus voteStatus, InputService inputService, OutputService outputService) {
        this.client = client;
        this.voteManager = voteManager;
        this.candidateInfo = candidateInfo;
        this.voteStatus = voteStatus;
        this.inputService = inputService;
        this.outputService = outputService;
    }

    private void waitForVotingToStart() throws InterruptedException, RemoteException {
        while (!voteStatus.isVotingStarted()) {
            outputService.printWaitingForVote();
            Thread.sleep(5000);
        }
    }
    private boolean voteEnded() throws RemoteException {
        if (voteStatus.isVotingEnded()){
            outputService.printVotingDone();
            return true;
        }
        return false;
    }
    private void handleReVoting() throws RemoteException, IncorrectOTPException {
        String previousOTP = inputService.getPreviousOTP();
        if (voteManager.validateAndRemovePreviousVote(client.getStudentNumber(), previousOTP)) {
            voteManager.generateOTP(client.getStudentNumber());
            outputService.printNewOTP(voteManager.getOTP(client.getStudentNumber()));
        }else{
            throw new IncorrectOTPException();
        }
    }

    public void executeVotingProcess() throws RemoteException, InterruptedException, IncorrectOTPException, IncorrectScoreException, VotingHasEndedException {
        if (voteEnded()){
            return;
        }
        waitForVotingToStart();
        if (voteManager.hasAlreadyVoted(client.getStudentNumber())){
            boolean wantsToRevote = inputService.askForRevoting();
            if (wantsToRevote) {
                handleReVoting();
                castVote();
            }
        } else{
            if (voteManager.getOTP(client.getStudentNumber())==null) {
                voteManager.generateOTP(client.getStudentNumber());
            }
            outputService.printOTP(voteManager.getOTP(client.getStudentNumber()));
            castVote();
        }
    }
    private synchronized void castVote() throws IncorrectOTPException, RemoteException, IncorrectScoreException, VotingHasEndedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("print your otp : ");
        String otp = scanner.nextLine();
        if (voteManager.validateOTP(client.getStudentNumber(), otp)){
            if (voteStatus.isVotingEnded()) {
                throw new VotingHasEndedException();
            }
            Map<Integer, Integer> candidateScores = inputService.getScoreForCandidates(candidateInfo.getCandidateList());
            if (voteStatus.isVotingEnded()) {
                throw new VotingHasEndedException();
            }else{
                voteManager.castVote(client.getStudentNumber(), candidateScores, voteManager.getOTP(client.getStudentNumber()));
                outputService.printVoteSuccess();
            }
        } else{
            throw new IncorrectOTPException();
        }
    }

}
