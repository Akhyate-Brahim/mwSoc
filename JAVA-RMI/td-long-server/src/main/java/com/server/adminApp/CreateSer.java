package com.server.adminApp;

import com.common.candidate.Candidate;
import com.common.candidate.CandidateWithPitch;
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
        candidates.add(new CandidateWithPitch("Charlie", "White", "https://www.youtube.com/watch?v=CHARLIE_VIDEO"));

        users.add(new User(101, "pass101"));
        users.add(new User(102, "pass102"));
        users.add(new User(103, "pass103"));
        users.add(new User(104, "pass104"));
        users.add(new User(105, "pass105"));
        users.add(new User(106, "pass106"));

        try (FileOutputStream fileOut = new FileOutputStream("src/main/resources/students.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(candidates);
            out.writeObject(users);
            out.writeInt(Candidate.getIdCounter());
            System.out.println("Serialized data is saved in students.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
