package com.server.candidate;
import java.io.Serializable;
import java.util.Optional;

public class Candidate implements Serializable {
    private static int idCounter = 0;
    private int rank;
    private String firstName;
    private String lastName;

    public Candidate(String firstName, String lastName) {
        this.rank = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "Candidate {" +
                "Rank=" + rank +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                '}';
    }
}
