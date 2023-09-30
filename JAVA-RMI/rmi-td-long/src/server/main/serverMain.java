package server.main;

import server.authentification.ILogin;
import server.authentification.Login;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class serverMain {
    public static void main(String[] args) {
        try {
            ILogin login = new Login();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LOGIN", login);
            System.out.println("Distante server is running...");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
