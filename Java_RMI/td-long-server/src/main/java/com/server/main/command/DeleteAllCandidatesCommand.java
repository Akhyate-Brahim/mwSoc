package com.server.main.command;

import com.server.adminApp.AdminApp;

public class DeleteAllCandidatesCommand implements Command {
    private AdminApp adminApp;

    public DeleteAllCandidatesCommand(AdminApp adminApp) {
        this.adminApp = adminApp;
    }

    @Override
    public void execute() {
        adminApp.deleteAllCandidates();
        System.out.println("All candidates deleted.");
    }
}
