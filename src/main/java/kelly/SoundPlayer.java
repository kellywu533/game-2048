package kelly;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundPlayer {
    private static final String AUDIO_DIR = "/System/Library/Sounds/";
    public enum Type {
        MOVE("Ping.aiff")
        , ILLEGAL("Basso.aiff")
        , CLICK("Bottle.aiff")
        ;

        File soundFile;
        Type(String fname) {
            this.soundFile = new File(AUDIO_DIR, fname);
        }

        public File getSoundFile() {
            return this.soundFile;
        }
    }

    private static Clip clip;

    public static void playSound(File f) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(f)) {
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(Type t) {
        playSound(t.getSoundFile());
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<10; i++) {
            System.out.println(i);
            playSound(Type.MOVE);
            Thread.sleep(5);
        }
    }
}