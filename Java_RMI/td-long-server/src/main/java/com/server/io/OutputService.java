package com.server.io;

import com.server.adminApp.AdminApp;

public class OutputService {
    public OutputService() {
    }

    public void serverIsRunning() {
        System.out.println("server is running...");
    }

    public void printUseSerializedObjects() {
        System.out.println("we will use the existing data...");
    }

    public void printAllInformation(AdminApp admin){
        System.out.println("Here is the data of the users and candidates:");
        System.out.println("\nCandidates:");
        System.out.println("-------------------------------------------------");
        admin.printAllCandidates();
        System.out.println("\nUsers:");
        System.out.println("-------------------------------------------------");
        admin.printAllUsers();
        System.out.println("-------------------------------------------------");
    }

    public void printMainMenu() {
        System.out.println("0. Use existing data");
        System.out.println("1. Modify data");
        System.out.println("2. Print all data");;
    }

    public void printModificationMenu() {
        System.out.println("1. Add User");
        System.out.println("2. Delete User");
        System.out.println("3. Add Candidate");
        System.out.println("4. Delete Candidate");
        System.out.println("5. Delete All Users");
        System.out.println("6. Delete All Candidates");
        System.out.println("0. Back to main menu");
    }

    public void printInvalidOption() {
        System.out.println("Invalid option, try again.");
    }

    public void printResult(int rank, int candidateId, int points, double percentage) {
        System.out.printf("Rank: %d, Candidate ID: %d, Points: %d, Percentage: %.2f%%\n", rank, candidateId, points, percentage);
    }

    public void printMessage(String s) {
        System.out.println(s);
    }
    public void printVoteGuide(){
        System.out.print("Enter commands (start/end) to begin or end the vote :  ");
    }
}
