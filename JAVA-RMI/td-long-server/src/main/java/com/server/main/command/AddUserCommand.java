package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.user.User;

public class AddUserCommand implements Command {
    private AdminApp adminApp;
    private int studentNumber;
    private String password;

    public AddUserCommand(AdminApp adminApp, int studentNumber, String password) {
        this.adminApp = adminApp;
        this.studentNumber = studentNumber;
        this.password = password;
    }

    @Override
    public void execute() {
        User newUser = new User(studentNumber, password);
        adminApp.addUser(newUser);
        System.out.println("User added: " + newUser);
    }
}
