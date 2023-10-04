package com.client.io;

import com.common.candidate.Candidate;

import java.util.List;

public class OutputService {
    public void printCandidateList(List<Candidate> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            System.out.println("No candidates available.");
            return;
        }
        System.out.println("\nList of Candidates:");
        System.out.println("-------------------------------------------------");
        candidates.forEach(candidate -> System.out.println(candidate.toString()));
        System.out.println("-------------------------------------------------");
    }

    public void printOTP(String otp){
        System.out.println("your one time password : "+otp);
    }
}

