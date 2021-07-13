package main.controller;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundPlayer {

    private static final String BACKGROUND_MUSIC_FILE = "Music.wav";
    private static final String PLAYER_DEAD_SOUND_FILE = "playershot.mp3";
    private final MediaPlayer musicPlayer;
    private final AudioClip playerDeadSoundPlayer;

    public SoundPlayer() {
        this.musicPlayer = new MediaPlayer(new Media(convertNameToUrl(BACKGROUND_MUSIC_FILE)));
        this.playerDeadSoundPlayer = new AudioClip(convertNameToUrl(PLAYER_DEAD_SOUND_FILE));
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

    public void playPlayerShotSound() {
        playerDeadSoundPlayer.play();
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
