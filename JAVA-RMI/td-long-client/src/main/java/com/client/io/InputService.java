package com.client.io;
import com.common.candidate.Candidate;

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

    public int getScoreForCandidate(Candidate candidate){
        System.out.println("whats your score for candidate "+candidate.getLastName()+" : ");
        int score = scanner.nextInt();
        scanner.nextLine();
        return score;
    }

}
