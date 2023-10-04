package com.common.candidate;


public class CandidateWithPitch extends Candidate {
    private String pitch;  // This can be either text or a URL to a video

    public CandidateWithPitch(String firstName, String lastName, String pitch) {
        super(firstName, lastName);
        this.pitch = pitch;
    }

    public String getPitch() {
        return pitch;
    }
}