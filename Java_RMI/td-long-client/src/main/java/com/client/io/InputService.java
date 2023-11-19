package com.client.io;
import com.common.candidate.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner;

    public InputService() {
        this.scanner = new Scanner(System.in);
    }

    public int getStudentNumber() {
        System.out.print("Student number: ");
        int number = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return number;
    }

    public String getPassword() {
        System.out.print("Password: ");
        return scanner.nextLine();
    }

    public Map<Integer,Integer> getScoreForCandidates(List<Candidate> candidates){
        Map<Integer,Integer> candidateScoreMap = new HashMap<>();
        for (Candidate candidate : candidates){
            System.out.println("whats your score for candidate "+candidate.getLastName()+" : ");
            int score = scanner.nextInt();
            candidateScoreMap.put(candidate.getRank(),score);
        }
        return candidateScoreMap;
    }
    public String getPreviousOTP() {
        System.out.print("Enter your previous OTP to re-vote: ");
        return scanner.next();
    }

    public boolean askForRevoting() {
        System.out.print("Do you want to re-vote? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }
    public String enterOTP() {
        System.out.println("Please enter your OTP:");
        return scanner.nextLine();
    }
    public boolean askForCurrentResults() {
        System.out.print("\nDo you want to view the current results? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }

    public boolean askForFinalResults() {
        System.out.print("\nDo you want to view the final results? (yes/no): ");
        return scanner.next().equalsIgnoreCase("yes");
    }
}
