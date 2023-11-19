package com.server.vote;

import com.common.candidate.Candidate;
import com.common.exceptions.IncorrectOTPException;
import com.common.exceptions.IncorrectScoreException;
import com.common.vote.IVoteManager;
import com.server.adminApp.AdminApp;
import com.server.io.OutputService;
import com.server.user.OTPCreator;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class VoteManager extends UnicastRemoteObject implements IVoteManager {
    private AdminApp adminApp;
    private VoteCallback voteCallback;
    private ConcurrentHashMap<Integer, Vote> votesRecord = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, String> usersOTPs = new ConcurrentHashMap<>();

    public VoteManager(AdminApp adminApp) throws RemoteException {
        this.adminApp = adminApp;
    }
    @Override
    public String getOTP(int studentNumber) throws RemoteException {
        return usersOTPs.get(studentNumber);
    }
    @Override
    public synchronized void castVote(int studentNumber, Map<Integer, Integer> candidateScores, String otp) throws RemoteException, IncorrectOTPException, IncorrectScoreException {
        for (int score : candidateScores.values()) {
            if (score < 0 || score > 3) {
                throw new IncorrectScoreException("Each score must be between 0 and 3.");
            }
        }
        Vote vote = new Vote(studentNumber, candidateScores);
        votesRecord.put(studentNumber, vote);
        if (voteCallback != null) {
            voteCallback.onVoteCast(studentNumber, vote.getFormattedVoteTime());
        }
    }
    @Override
    public boolean hasAlreadyVoted(int studentNumber) throws RemoteException {
        return votesRecord.containsKey(studentNumber);
    }

    @Override
    public boolean validateAndRemovePreviousVote(int studentNumber, String otp) throws RemoteException, IncorrectOTPException {
        if (usersOTPs.get(studentNumber).equals(otp)) {
            votesRecord.remove(studentNumber);
            return true;
        }
        return false;
    }

    @Override
    public void generateOTP(int studentNumber) throws RemoteException {
        usersOTPs.put(studentNumber, OTPCreator.generate());
    }

    public void setAdminApp(AdminApp adminApp) {
        this.adminApp = adminApp;
    }
    @Override
    public boolean validateOTP(int studentNumber, String otp) throws RemoteException {
        String existingOTP = usersOTPs.get(studentNumber);
        return existingOTP.equals(otp);
    }

    public Map<Integer, Integer> retrievePoints() {
        Map<Integer, Integer> totalScores = new HashMap<>();

        for (Vote vote : votesRecord.values()) {
            Map<Integer, Integer> candidateScores = vote.getCandidateScores();
            for (Map.Entry<Integer, Integer> entry : candidateScores.entrySet()) {
                totalScores.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }

        return totalScores;
    }
    public Map<Integer, Integer> sortCandidatesByPoints(Map<Integer, Integer> totalScores) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(totalScores.entrySet());
        list.sort(Map.Entry.<Integer, Integer>comparingByValue().reversed());

        Map<Integer, Integer> sortedScores = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            sortedScores.put(entry.getKey(), entry.getValue());
        }

        return sortedScores;
    }

    public synchronized void displayResults(OutputService outputService) throws RemoteException {
        Map<Integer, Integer> totalScores = retrievePoints();
        Map<Integer, Integer> sortedScores = sortCandidatesByPoints(totalScores);
        int totalVotes = totalScores.values().stream().mapToInt(Integer::intValue).sum();

        outputService.printMessage("Voting Results:");
        List<Candidate> candidates = adminApp.getCandidateList();

        for (Map.Entry<Integer, Integer> entry : sortedScores.entrySet()) {
            int rank = entry.getKey();
            int votes = entry.getValue();
            double percentage = (votes * 100.0) / totalVotes;

            Candidate candidate = findCandidateByRank(candidates, rank);
            if (candidate != null) {
                String candidateName = candidate.getLastName();

                outputService.printMessage(String.format("%s: %d votes (%.2f%%)", candidateName, votes, percentage));
            } else {
                outputService.printMessage(String.format("Candidate with rank %d not found.", rank));
            }
        }
    }

    private Candidate findCandidateByRank(List<Candidate> candidates, int rank) {
        for (Candidate candidate : candidates) {
            if (candidate.getRank() == rank) {
                return candidate;
            }
        }
        return null;
    }
    @Override
    public synchronized String getVotingResults() throws RemoteException {
        Map<Integer, Integer> totalScores = retrievePoints();
        Map<Integer, Integer> sortedScores = sortCandidatesByPoints(totalScores);
        int totalVotes = totalScores.values().stream().mapToInt(Integer::intValue).sum();

        StringBuilder results = new StringBuilder("Voting Results:\n");
        List<Candidate> candidates = adminApp.getCandidateList();

        for (Map.Entry<Integer, Integer> entry : sortedScores.entrySet()) {
            int rank = entry.getKey();
            int votes = entry.getValue();
            double percentage = (votes * 100.0) / totalVotes;

            Candidate candidate = findCandidateByRank(candidates, rank);
            if (candidate != null) {
                String candidateName = candidate.getLastName();
                results.append(String.format("%s: %d votes (%.2f%%)\n", candidateName, votes, percentage));
            } else {
                results.append(String.format("Candidate with rank %d not found.\n", rank));
            }
        }

        return results.toString();
    }
    public void setVoteCallback(VoteCallback voteCallback) {
        this.voteCallback = voteCallback;
    }



}
