package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.candidate.Candidate;

public class DeleteCandidateCommand implements Command {
    private AdminApp adminApp;
    private Candidate candidate;

    public DeleteCandidateCommand(AdminApp adminApp, Candidate candidate) {
        this.adminApp = adminApp;
        this.candidate = candidate;
    }

    @Override
    public void execute() {
        adminApp.deleteCandidate(candidate);
        System.out.println("Candidate deleted: " + candidate);
    }
}
