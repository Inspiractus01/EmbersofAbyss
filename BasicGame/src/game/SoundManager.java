package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundManager {
    private Clip backgroundMusic;

    public void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);

            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music
            backgroundMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void playSound(String filePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

}
