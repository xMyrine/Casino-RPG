package cz.cvut.fel.pjv;

import javax.sound.sampled.Clip;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[20];

    public Sound() {

        try {
            soundURL[0] = getClass().getResource("/sounds/bg_music_1.wav");
            soundURL[1] = getClass().getResource("/sounds/coin.wav");
            soundURL[2] = getClass().getResource("/sounds/drinking_beer.wav");
            soundURL[3] = getClass().getResource("/sounds/slot_machine.wav");
            soundURL[4] = getClass().getResource("/sounds/door.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void declareFile(int i) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopSound() {
        clip.stop();
    }

    public void loopSound() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playSound() {
        clip.start();
    }

    /*
     * 0 - background music
     */
    public void playMusic() {
        this.declareFile(0);
        this.playSound();

        // Check if the audio system supports volume control
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            // Get the volume control
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // Reduce the volume by 10 decibels
            float currentVolume = volume.getValue();
            volume.setValue(currentVolume - 20.0f);
        }
        this.loopSound();
    }

    /*
     * Plays sound based on the index
     * 1 - chip
     * 2 - beer
     * 3 - slot machine
     */

    public void playMusic(int i) {
        if (i == 0) {
            this.loopSound();
        } else {
            this.declareFile(i);
            this.playSound();
        }
    }
}
