package common;

import java.io.Serializable;

public class Resultat implements Serializable {
    private static final long serialVersionUID = 1L;
    private String infoCB;

    private int score;
    public Resultat(int score,String infoCB) {
        this.score = score;
        this.infoCB=infoCB;
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

    public void setInfoCB(String infoCB) {
        this.infoCB = infoCB;
    }
}
