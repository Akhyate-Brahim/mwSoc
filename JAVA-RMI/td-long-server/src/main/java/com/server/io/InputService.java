package com.server.io;

import com.server.candidate.Candidate;
import com.server.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputService {
    private final Scanner scanner = new Scanner(System.in);

    public InputService() {
    }

    public Candidate getNewCandidate() {
        System.out.print("Enter candidate's first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter candidate's last name: ");
        String lastName = scanner.nextLine();

        return new Candidate(firstName, lastName);
    }

    public User getNewUser() {
        System.out.print("Enter user's student number: ");
        int studentNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline left-over
        System.out.print("Enter user's password: ");
        String password = scanner.nextLine();
        return new User(studentNumber, password); // Create a new user
    }

    public String getUserResponse() {
        System.out.print("Your choice: ");
        return scanner.nextLine().trim();
    }
    public int getUserOption() {
        System.out.print("Select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return choice;
    }

    public int getStudentNumber() {
        System.out.print("Enter the student number of the user to delete: ");
        int studentNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return studentNumber;
    }

    public int getCandidateRank() {
        System.out.print("Enter the rank of the candidate to delete: ");
        int rank = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        return rank;
    }

    public void waitForEnter() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine(); // Wait for user to press Enter
    }

    public void close() {
        scanner.close();
    }
}

