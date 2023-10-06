package com.client.io;

import com.common.candidate.Candidate;

import java.util.List;

public class OutputService {
    public void printCandidateList(List<Candidate> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }
        System.out.println("\nList of Candidates:");
        System.out.println("-------------------------------------------------");
        candidates.forEach(candidate -> System.out.println(candidate.toString()));
        System.out.println("-------------------------------------------------");
    }

    public void printOTP(String otp){
        System.out.println("your one time password : "+otp);
    }

    public void printVoteSuccess() {
        System.out.println("Your vote has been successfully cast!");
    }

    public void printAlreadyVotedError() {
        System.out.println("You have already voted. You cannot vote more than once.");
    }

    public void printVotingNotStartedError() {
        System.out.println("Voting has not started yet. Please wait for the voting to begin.");
    }

    public void printWaitingForVote() {
        System.out.println("Waiting for the vote to start...");
    }

    public void printMessage(String s) {
        System.out.println(s);
    }

    public void printVotingDone() {
        System.out.println("the vote is closed");
    }

    public void printNewOTP(String otp) {
        System.out.println("here is your new OTP : "+otp);
    }
}

