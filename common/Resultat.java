package common;

public class Resultat {

    private int score;
    public Resultat(int score) {
        this.score = score;
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
                "score=" + score +
                '\'' +
                '}';
    }
}
