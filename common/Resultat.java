package common;

import java.io.Serializable;

public class Resultat implements Serializable {
    private transient int pin;

    private int score;
    public Resultat(int score,int pin) {
        this.score = score;
        this.pin=pin;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "Resultat {" +
                "score=" + score +" , pin="+pin+
                '\'' +
                '}';
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
