package server.candidate;
import java.util.Optional;

public class Candidate {
    private static int idCounter = 0;
    private int rank;
    private String firstName;
    private String lastName;
    private Optional<Pitch> pitch;

    public Candidate(String firstName, String lastName, Optional<Pitch> pitch) {
        this.rank = ++idCounter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pitch = pitch;
    }

}
