package com.server.adminApp;

import com.server.candidate.Candidate;
import com.server.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminApp {
    private List<User> userList;
    private List<Candidate> candidateList;
    private final String filePath = "src/main/resources/students.ser";

    public AdminApp() {
        this.userList = new ArrayList<>();
        this.candidateList = new ArrayList<>();
        deserialize();
    }

    private void deserialize() {
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            candidateList = (List<Candidate>) in.readObject();
            userList = (List<User>) in.readObject();
            System.out.println("Data has been deserialized");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error occurred during deserialization");
        }
    }

    private void serialize() {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(candidateList);
            out.writeObject(userList);
            System.out.println("Data has been serialized");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred during serialization");
        }
    }

    public void addUser(User user) {
        userList.add(user);
        serialize();
    }

    public void deleteUser(User user) {
        userList = userList.stream()
                .filter(u -> !u.equals(user))
                .collect(Collectors.toList());
        serialize();
    }

    public void deleteAllUsers() {
        userList.clear();
        serialize();
    }

    public void addCandidate(Candidate candidate) {
        candidateList.add(candidate);
        serialize();
    }

    public void deleteCandidate(Candidate candidate) {
        candidateList = candidateList.stream()
                .filter(c -> !c.equals(candidate))
                .collect(Collectors.toList());
        serialize();
    }

    public void deleteAllCandidates() {
        candidateList.clear();
        serialize();
    }

    public List<User> getUserList() {
        return userList;
    }

    public List<Candidate> getCandidateList() {
        return candidateList;
    }

    public void printAllUsers() {
        if (userList.isEmpty()) {
            System.out.println("No users available.");
            return;
        }
        System.out.println("Users:");
        for (User user : userList) {
            System.out.println(user);
        }
    }

    public void printAllCandidates() {
        if (candidateList.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }

        System.out.println("Candidates:");
        for (Candidate candidate : candidateList) {
            System.out.println(candidate);
        }
    }
    public static void main(String[] args) {
        AdminApp adminApp = new AdminApp();

        // Adding a new candidate
        Candidate newCandidate = new Candidate("John", "Doe");
        adminApp.addCandidate(newCandidate);

        // Adding a new user
        User newUser = new User(107, "pass107");
        adminApp.addUser(newUser);

        // Printing updated lists
        System.out.println("Updated Candidate List: " + adminApp.getCandidateList());
        System.out.println("Updated User List: " + adminApp.getUserList());

        // Deleting all candidates and users
        adminApp.deleteAllCandidates();
        adminApp.deleteAllUsers();

        // Printing updated lists after deletion
        System.out.println("Updated Candidate List After Deletion: " + adminApp.getCandidateList());
        System.out.println("Updated User List After Deletion: " + adminApp.getUserList());
    }
}
