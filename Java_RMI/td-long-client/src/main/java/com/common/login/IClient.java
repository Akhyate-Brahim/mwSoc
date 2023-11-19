package com.common.login;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    int getStudentNumber() throws RemoteException;
    String getPassword() throws RemoteException;
}
