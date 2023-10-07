package com.common.candidate;
import java.io.Serializable;

public class Candidate implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int idCounter = 0;
    private int rank;
    private String firstName;
    private String lastName;

    public Candidate(String firstName, String lastName) {
        this.rank = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static void setIdCounter(int readInt) {
        idCounter=readInt;
    }

    public static int getIdCounter() {
        return idCounter;
    }

    @Override
    public String toString() {
        return String.format("Rank: %-5d | First Name: %-15s | Last Name: %-15s",
                rank, firstName, lastName);
    }
    public String getLastName() {
        return lastName;
    }
    public int getRank() {
        return rank;
    }
}
