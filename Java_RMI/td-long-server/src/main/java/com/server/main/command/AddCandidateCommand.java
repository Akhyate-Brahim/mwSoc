package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.common.candidate.Candidate;

public class AddCandidateCommand implements Command {
    private AdminApp adminApp;
    private Candidate candidate;

    public AddCandidateCommand(AdminApp adminApp, Candidate candidate) {
        this.adminApp = adminApp;
        this.candidate = candidate;
    }

    @Override
    public void execute() {
        adminApp.addCandidate(candidate);
        System.out.println("Candidate added: " + candidate);
    }
}
