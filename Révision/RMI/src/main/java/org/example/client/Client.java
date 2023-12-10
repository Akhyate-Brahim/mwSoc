package org.example.client;

import org.example.server.ICalculator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry reg = LocateRegistry.getRegistry(1099);
        ICalculator calculator = (ICalculator) reg.lookup("Calculator");
        System.out.println(calculator.add(1,2));
    }
}
