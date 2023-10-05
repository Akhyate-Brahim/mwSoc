package com.server.vote;

import com.common.candidate.Candidate;
import com.common.exceptions.AlreadyUsedOTPException;
import com.common.exceptions.HasAlreadyVotedException;
import com.common.exceptions.IncorrectScoreException;
import com.common.vote.IVotingMaterial;
import com.server.adminApp.AdminApp;
import com.server.user.OTPCreator;
import com.server.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VotingMaterial extends UnicastRemoteObject implements IVotingMaterial {
    private AdminApp adminApp;
    private ConcurrentHashMap<Integer, Integer> votes = new ConcurrentHashMap<>();  // Initialized here
    private ConcurrentHashMap<Integer, String> usersOTPs = new ConcurrentHashMap<>();  // Initialized here

    public VotingMaterial(AdminApp adminApp) throws RemoteException {
        this.adminApp = adminApp;
        initializeVotes();
        initializeUsersOTPs();
    }

    private void initializeVotes() {
        for (Candidate candidate : adminApp.getCandidateList()){
            votes.put(candidate.getRank(), 0);
        }
    }

    private void initializeUsersOTPs() {
        for (User user : adminApp.getUserList()){
            usersOTPs.put(user.getStudentNumber(),"");
        }
    }


        @Override
    public String getOTP(int studentNumber) throws RemoteException {
        return usersOTPs.get(studentNumber);
    }

    @Override
    public void castVote(int studentNumber, Map<Integer, Integer> candidateScores, String otp) throws RemoteException, AlreadyUsedOTPException, IncorrectScoreException, HasAlreadyVotedException {
        if (checkOTP(studentNumber,otp)){
            for (Map.Entry<Integer, Integer> entry : candidateScores.entrySet()) {
                int candidateRank = entry.getKey();
                int score = entry.getValue();
                if (score < 0 || score > 3) {
                    throw new IncorrectScoreException("Score for candidate " + candidateRank + " is out of range.");
                }
                votes.put(candidateRank, votes.get(candidateRank) + score);
            }
            usersOTPs.put(studentNumber , "VOTED");
        } else {
            throw new AlreadyUsedOTPException();
        }

    }
    public boolean checkOTP(int studentNumber, String otp) {
        return usersOTPs.get(studentNumber).equals(otp) && !usersOTPs.get(studentNumber).equals("VOTED");
    }

    @Override
    public void generateOTP(int studentNumber) throws RemoteException {
        if (usersOTPs.get(studentNumber).equals("")){
            usersOTPs.put(studentNumber,OTPCreator.generate());
        }
    }

    public void setAdminApp(AdminApp adminApp) {
        this.adminApp = adminApp;
    }
}
