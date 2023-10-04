package com.server.login;

import com.common.login.IClient;
import com.common.vote.IVotingMaterial;
import com.server.adminApp.AdminApp;
import com.common.login.ILogin;
import com.common.exceptions.BadCredentialsException;
import com.server.user.User;
import com.server.vote.VotingMaterial;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Login extends UnicastRemoteObject implements ILogin {
    AdminApp adminApp;

    public List<User> getUsers() {
        return adminApp.getUserList();
    }
    public Login(AdminApp adminApp) throws RemoteException {
        this.adminApp = adminApp;
    }

    @Override
    public IVotingMaterial requestVotingMaterial(IClient client) throws RemoteException, BadCredentialsException {
        int studentNumber = client.getStudentNumber();
        String password = client.getPassword();

        if (isValidUser(studentNumber, password)) {
            return new VotingMaterial(adminApp);
        } else {
            throw new BadCredentialsException();
        }
    }

    private boolean isValidUser(int studentNumber, String password) {
        for (User user : adminApp.getUserList()){
            if ((user.getStudentNumber() == studentNumber) && (password.equals(user.getPassword()))){
                return true;
            }
        }
        return false;
    }
}
