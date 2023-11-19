package com.server.user;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private int studentNumber;
    private String password;

    public User(int studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("Student Number: %-15d | Password: %-15s",
                studentNumber, password);
    }

}

