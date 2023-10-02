package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.candidate.Candidate;

public class AddCandidateCommand implements Command {
    private AdminApp adminApp;
    private String firstName;
    private String lastName;

    public AddCandidateCommand(AdminApp adminApp, String firstName, String lastName) {
        this.adminApp = adminApp;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void execute() {
        Candidate newCandidate = new Candidate(firstName, lastName);
        adminApp.addCandidate(newCandidate);
        System.out.println("Candidate added: " + newCandidate);
    }
}
