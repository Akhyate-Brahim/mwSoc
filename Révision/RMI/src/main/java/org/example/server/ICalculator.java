package org.example.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalculator extends Remote {
    int add(int i, int y) throws RemoteException;
    int multiply(int x, int y) throws RemoteException;
}
