package server.candidate;

public class TextPitch implements Pitch {
    private String text;

    public TextPitch(String text) {
        this.text = text;
    }

    @Override
    public void displayPitch() {
        System.out.println("Text Pitch: " + text);
    }
}
