package client.main;
import client.io.*;
import server.authentification.ILogin;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
            ILogin login = (ILogin) reg.lookup("LOGIN");
            InputService inputService = new InputService();
            OutputService outputService = new OutputService();
            int studentNumber = inputService.getStudentNumber();
            String password = inputService.getPassword();
            boolean isSuccess = login.isSuccess(studentNumber, password);
            outputService.printLoginResult(isSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}