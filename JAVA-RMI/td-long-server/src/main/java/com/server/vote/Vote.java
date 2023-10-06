package com.server.vote;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Vote {
    private int studentNumber;
    private Map<Integer, Integer> candidateScores;
    private LocalDateTime voteTime;


    public Vote(int studentNumber, Map<Integer, Integer> candidateScores) {
        this.studentNumber = studentNumber;
        this.candidateScores = candidateScores;
        this.voteTime = LocalDateTime.now();
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public Map<Integer, Integer> getCandidateScores() {
        return candidateScores;
    }
    public String getFormattedVoteTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return voteTime.format(formatter);
    }
}
