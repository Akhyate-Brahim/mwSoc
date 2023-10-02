package com.server.login;

import com.server.candidate.Candidate;
import com.common.login.ILogin;
import com.server.user.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Login extends UnicastRemoteObject implements ILogin {

    List<Candidate> candidates;
    List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public Login() throws RemoteException {}

    @Override
    public boolean isSuccess(int studentNumber, String password) throws RemoteException {
        for (User user : users){
            if (studentNumber==user.getStudentNumber() && password.equals(user.getPassword())){
                return true;
            }
        }
        return false;
    }
}
