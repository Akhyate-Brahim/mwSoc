package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.common.candidate.Candidate;

public class DeleteCandidateCommand implements Command {
    private AdminApp adminApp;
    private int rank;

    public DeleteCandidateCommand(AdminApp adminApp, int rank) {
        this.adminApp = adminApp;
        this.rank = rank;
    }

    @Override
    public void execute() {
        Candidate candidate = adminApp.getCandidateByRank(rank);
        if (candidate != null) {
            adminApp.deleteCandidate(candidate);
            System.out.println("Candidate deleted: " + candidate);
        } else {
            System.out.println("Candidate with rank " + rank + " not found.");
        }
    }
}
