package com.server.adminApp;

import com.common.candidate.Candidate;
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
            Candidate.setIdCounter(in.readInt());  // Deserializing the idCounter
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
            out.writeInt(Candidate.getIdCounter());
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

    public Candidate getCandidateByRank(int rank) {
        return candidateList.stream()
                .filter(candidate -> candidate.getRank() == rank)
                .findFirst()
                .orElse(null);
    }

    public User getUserByStudentNumber(int studentNumber) {
        return userList.stream()
                .filter(user -> user.getStudentNumber() == studentNumber)
                .findFirst()
                .orElse(null);
    }
}
