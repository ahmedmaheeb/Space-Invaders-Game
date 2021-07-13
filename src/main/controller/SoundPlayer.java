package main.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundPlayer {

    private static final String BACKGROUND_MUSIC_FILE = "Music.wav";
    private final MediaPlayer musicPlayer;

    public SoundPlayer() {
        this.musicPlayer = new MediaPlayer(new Media(convertNameToUrl(BACKGROUND_MUSIC_FILE)));
    }

    public void playMusic() {
        if (isPlayingMusic()) {
            return;
        }
        // Loop for the main music sound:
        this.musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        this.musicPlayer.play();
    }

    public void stopMusic() {
        if (isPlayingMusic()) {
            this.musicPlayer.stop();
        }
    }

    private String convertNameToUrl(String fileName) {
        URL musicSourceUrl = getClass().getClassLoader().getResource(fileName);
        if (musicSourceUrl == null) {
            throw new IllegalArgumentException(String.format("The file %s could not be found", fileName));
        }
        return musicSourceUrl.toExternalForm();
    }

    public boolean isPlayingMusic() {
        return MediaPlayer.Status.PLAYING.equals(this.musicPlayer.getStatus());
    }
}
