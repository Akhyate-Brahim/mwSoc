package com.server.main;

import com.common.login.IClient;
import com.common.login.ILogin;
import com.common.vote.IVotingMaterial;
import com.server.adminApp.AdminApp;
import com.server.io.InputService;
import com.server.login.Login;
import com.server.io.OutputService;
import com.server.main.command.Menu;
import com.server.vote.VotingMaterial;

import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class serverMain {
    public static void main(String[] args) {
        try {
            AdminApp adminApp = new AdminApp();
            OutputService outputService=new OutputService();
            InputService inputService=new InputService();
            ILogin login = new Login(adminApp);
            IVotingMaterial votingMaterial = new VotingMaterial(adminApp);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LOGIN", login);
            registry.rebind("VOTING MATERIAL",votingMaterial);
            outputService.serverIsRunning();
            Menu menu = new Menu(adminApp, outputService, inputService);
            menu.display();
            inputService.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
