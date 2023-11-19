package com.server.main;

import com.common.login.ILogin;
import com.common.vote.ICandidateInfo;
import com.common.vote.IVoteStatus;
import com.common.vote.IVoteManager;
import com.server.adminApp.AdminApp;
import com.server.io.InputService;
import com.server.login.Login;
import com.server.io.OutputService;
import com.server.main.command.Menu;
import com.server.vote.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain implements VoteCallback {
    @Override
    public void onVoteCast(int studentNumber, String info) {
        System.out.println("Vote cast by student number " + studentNumber + " at " + info);
    }
    public static void main(String[] args) {
        try {
            AdminApp adminApp = new AdminApp();
            OutputService outputService=new OutputService();
            InputService inputService=new InputService();
            IVoteManager votingMaterial = new VoteManager(adminApp);
            ((VoteManager) votingMaterial).setVoteCallback(new ServerMain());
            ILogin login = new Login(adminApp, votingMaterial);
            ICandidateInfo candidateInfo = new CandidateInfo(adminApp);
            IVoteStatus voteStatus = new VoteStatus();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LOGIN", login);
            registry.rebind("VOTING MATERIAL",votingMaterial);
            registry.rebind("ICANDIDATEINFO", candidateInfo);
            registry.rebind("VOTESTATUS", voteStatus);
            outputService.serverIsRunning();
            Menu menu = new Menu(adminApp, outputService, inputService);
            menu.display();
            outputService.printVoteGuide();
            while (true) {
                String command = inputService.getCommand();
                if (command.equalsIgnoreCase("start")) {
                    voteStatus.startVote();
                } else if (command.equalsIgnoreCase("end")) {
                    voteStatus.endVote();
                    ((VoteManager) votingMaterial).displayResults(outputService);
                    inputService.close();
                    break; //
                } else {
                    System.out.println("Unknown command. Type 'start' to begin the vote or 'end' to finish it.");
                }
                Thread.sleep(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
