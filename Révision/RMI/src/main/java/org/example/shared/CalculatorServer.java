package org.example.shared;

import org.example.server.ICalculator;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer extends UnicastRemoteObject implements ICalculator {
    protected CalculatorServer() throws RemoteException {
        super();
    }

    @Override
    public int add(int i, int y) throws RemoteException {
        return i+y;
    }

    @Override
    public int multiply(int x, int y) throws RemoteException {
        return x*y;
    }
    public static void main(String[] args) throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(1099);
        ICalculator calculator = new CalculatorServer();
        reg.rebind("Calculator", calculator);
    }
}
