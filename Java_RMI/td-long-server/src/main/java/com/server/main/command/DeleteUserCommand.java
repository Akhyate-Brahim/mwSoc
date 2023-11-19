package com.server.main.command;

import com.server.adminApp.AdminApp;
import com.server.user.User;

public class DeleteUserCommand implements Command {
    private AdminApp adminApp;
    private int studentNumber;

    public DeleteUserCommand(AdminApp adminApp, int studentNumber) {
        this.adminApp = adminApp;
        this.studentNumber = studentNumber;
    }

    @Override
    public void execute() {
        User user = adminApp.getUserByStudentNumber(studentNumber);
        if (user != null) {
            adminApp.deleteUser(user);
            System.out.println("User deleted: " + user);
        } else {
            System.out.println("User with student number " + studentNumber + " not found.");
        }
    }
}
