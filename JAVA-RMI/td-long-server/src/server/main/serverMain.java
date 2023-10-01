package server.main;

import common.login.ILogin;
import server.login.Login;
import server.io.OutputService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class serverMain {
    public static void main(String[] args) {
        try {
            OutputService outputService=new OutputService();
            ILogin login = new Login();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LOGIN", login);
            outputService.serverIsRunning();

            // fill in the users and candidates

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
