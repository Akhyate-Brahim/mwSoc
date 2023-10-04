package com.client.interaction;


import com.common.login.IClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {
    private int studentNumber;
    private String password;

    public Client(int studentNumber,String password) throws RemoteException {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    @Override
    public String getPassword() throws RemoteException {
        return this.password;
    }
    @Override
    public int getStudentNumber() throws RemoteException {
        return this.studentNumber;
    }
}
