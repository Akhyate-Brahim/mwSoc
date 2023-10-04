package com.client.io;
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
}
