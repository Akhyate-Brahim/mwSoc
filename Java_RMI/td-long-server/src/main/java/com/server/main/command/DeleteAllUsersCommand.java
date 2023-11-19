package com.server.main.command;

import com.server.adminApp.AdminApp;

public class DeleteAllUsersCommand implements Command {
    private AdminApp adminApp;

    public DeleteAllUsersCommand(AdminApp adminApp) {
        this.adminApp = adminApp;
    }

    @Override
    public void execute() {
        adminApp.deleteAllUsers();
        System.out.println("All users deleted.");
    }
}
