package cz.cvut.fel.pjv;

import javax.sound.sampled.Clip;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;

/**
 * Sound is a class that plays sounds in the game.
 * 
 * @Author Minh Tu Pham
 */
public class Sound {
    private Clip clip;
    private URL[] soundURL = new URL[20];
    private boolean isMuted = false;
    private Clip bgMusicClip;

    public Sound() {

        try {
            soundURL[0] = getClass().getResource("/sounds/bg_music_1.wav");
            soundURL[1] = getClass().getResource("/sounds/coin.wav");
            soundURL[2] = getClass().getResource("/sounds/drinking_beer.wav");
            soundURL[3] = getClass().getResource("/sounds/slot_machine.wav");
            soundURL[4] = getClass().getResource("/sounds/door.wav");
            soundURL[5] = getClass().getResource("/sounds/chest_open.wav");
            soundURL[6] = getClass().getResource("/sounds/victory.wav");
            soundURL[7] = getClass().getResource("/sounds/shooting.wav");
            soundURL[8] = getClass().getResource("/sounds/dice_roll.wav");
            soundURL[9] = getClass().getResource("/sounds/pokachu.wav");
            soundURL[10] = getClass().getResource("/sounds/con_te_partiro_nocp.wav");
            soundURL[19] = getClass().getResource("/sounds/Megalovania_no_CP.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Declares the file based on the index
     * 
     * @param i - index of the sound
     */
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

    /**
     * Plays background music
     * 0 - background music
     */
    public void playMusic() {
        this.declareFile(0);
        this.playSound();

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float currentVolume = volume.getValue();
            volume.setValue(currentVolume - 20.0f);
        }
        this.loopSound();
        this.bgMusicClip = clip;
    }

    /**
     * Toggles sound on and off
     */
    public void toggleSound() {
        if (bgMusicClip == null) {
            return;
        }

        if (isMuted) {
            bgMusicClip.start();
            isMuted = false;
        } else {
            bgMusicClip.stop();
            isMuted = true;
        }
    }

    /**
     * Plays sound based on the index
     * 
     * 1 - chip
     * 2 - beer
     * 3 - slot machine
     * 4 - door
     * 5 - chest
     * 6 - victory
     * 7 - shooting
     * 8 - dice roll
     * 
     * @param i - index of the sound
     */

    public void playMusic(int i) {
        if (i == 0) {
            this.loopSound();
        } else {
            this.declareFile(i);
            this.playSound();
        }

        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
        }
    }

    /**
     * Plays a new sound instead of the background music
     * This method is used for the end of the game
     * 
     * @param i - index of the sound
     */
    public void playNewSound(int i) {
        // Stop the background music
        if (bgMusicClip != null && bgMusicClip.isRunning()) {
            bgMusicClip.stop();
        }

        // Play the new sound
        this.playMusic(i);
    }
}
