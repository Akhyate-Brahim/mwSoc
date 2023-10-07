package com.server.login;

import com.common.login.IClient;
import com.common.vote.IVoteManager;
import com.server.adminApp.AdminApp;
import com.common.login.ILogin;
import com.common.exceptions.BadCredentialsException;
import com.server.user.User;
import com.server.vote.VoteManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Login extends UnicastRemoteObject implements ILogin {
    AdminApp adminApp;
    IVoteManager votingMaterial;

    public List<User> getUsers() {
        return adminApp.getUserList();
    }

    public Login(AdminApp adminApp, IVoteManager votingMaterial) throws RemoteException {
        this.adminApp = adminApp;
        this.votingMaterial = votingMaterial;
    }

    @Override
    public IVoteManager requestVotingMaterial(IClient client) throws RemoteException, BadCredentialsException {
        int studentNumber = client.getStudentNumber();
        String password = client.getPassword();

        if (isValidUser(studentNumber, password)) {
            ((VoteManager) votingMaterial).setAdminApp(adminApp);
            return votingMaterial;
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
