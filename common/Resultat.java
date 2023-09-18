package common;

import java.io.Serializable;

public class Resultat implements Serializable {
    private String infoCB;

    private int score;
    public Resultat(int score,String pin) {
        this.score = score;
        this.infoCB=pin;
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
                "score=" + score +" , infoCB="+infoCB+
                '\'' +
                '}';
    }

    public String getInfoCB() {
        return infoCB;
    }

    public void setInfoCB(String pin) {
        this.infoCB = pin;
    }
}
