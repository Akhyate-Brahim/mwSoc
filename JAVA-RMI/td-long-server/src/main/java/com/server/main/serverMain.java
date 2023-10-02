package com.server.main;

import com.common.login.ILogin;
import com.server.adminApp.AdminApp;
import com.server.io.InputService;
import com.server.login.Login;
import com.server.io.OutputService;
import com.server.main.command.Menu;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class serverMain {
    public static void main(String[] args) {
        try {
            AdminApp adminApp = new AdminApp();
            OutputService outputService=new OutputService();
            InputService inputService=new InputService();
            ILogin login = new Login();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LOGIN", login);
            outputService.serverIsRunning();
            Menu menu = new Menu(adminApp, outputService, inputService);
            menu.display();
            inputService.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
