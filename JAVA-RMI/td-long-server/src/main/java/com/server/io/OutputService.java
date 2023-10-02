package com.server.io;

import com.server.adminApp.AdminApp;

public class OutputService {
    public OutputService() {
    }

    public void serverIsRunning() {
        System.out.println("server is running...");
    }

    public void printUseSerializedObjects() {
        System.out.println("we will use an existing serialized file...");
    }

    public void printAddCandidates() {
        System.out.println("do you want to modify the candidates ? [Y/n]");
    }

    public void PrintAddUsers() {
        System.out.println("do you want to modify the users ? [Y/n]");
    }

    public void printAllInformation(AdminApp admin){
        System.out.println("here is the data of the users and candidates : ");
        admin.printAllCandidates();
        admin.printAllUsers();
    }
}
