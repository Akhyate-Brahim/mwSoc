package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.user.User;

public class DeleteUserCommand implements Command {
    private AdminApp adminApp;
    private User user;

    public DeleteUserCommand(AdminApp adminApp, User user) {
        this.adminApp = adminApp;
        this.user = user;
    }

    @Override
    public void execute() {
        adminApp.deleteUser(user);
        System.out.println("User deleted: " + user);
    }
}
