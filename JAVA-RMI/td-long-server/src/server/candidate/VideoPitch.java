package server.candidate;

public class VideoPitch implements Pitch {
    private String videoUrl;

    public VideoPitch(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public void displayPitch() {
        System.out.println("Video Pitch URL: " + videoUrl);
    }
}
