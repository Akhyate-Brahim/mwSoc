package com.server.adminApp;

import com.server.candidate.Candidate;
import com.server.candidate.CandidateWithPitch;
import com.server.user.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateSer {
    public static void main(String[] args) {
        List<Candidate> candidates = new ArrayList<>();
        List<User> users = new ArrayList<>();

        candidates.add(new Candidate("Frank", "Miller"));
        candidates.add(new Candidate("Grace", "Smith"));

        candidates.add(new CandidateWithPitch("Alice", "Johnson", "This is Alice's text pitch."));
        candidates.add(new CandidateWithPitch("Bob", "Brown", "https://www.youtube.com/watch?v=BOB_VIDEO"));

        users.add(new User(101, "pass101"));
        users.add(new User(102, "pass102"));
        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/students.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(candidates);
            out.writeObject(users);
            out.writeInt(Candidate.getIdCounter());  // Add this line to serialize the idCounter
            System.out.println("Serialized data is saved in students.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

