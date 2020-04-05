package kelly;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
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
        , GAME_OVER("Sosumi.aiff")
        ;

        File soundFile;
        Type(String fname) {
            this.soundFile = new File(AUDIO_DIR, fname);
        }

        public File getSoundFile() {
            return this.soundFile;
        }
    }

    public static void playSound(File f) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(f)) {
            final Clip clip = AudioSystem.getClip();
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    LineEvent.Type type = event.getType();
                    if (type == LineEvent.Type.OPEN) {
                        System.out.println("OPEN :"
//                                + clip + " - " + ais
                        );
                    } else if (type == LineEvent.Type.CLOSE) {
                        System.out.println("CLOSE:"
//                                + clip + " - " + ais
                        );
                    } else if (type == LineEvent.Type.START) {
                        System.out.println("START:"
//                                + clip + " - " + ais
                        );
                    } else if (type == LineEvent.Type.STOP) {
                        System.out.println("STOP :"
//                                + clip + " - " + ais
                        );
                        clip.close();
                    }
                }
            });
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
//        for(int i=0; i<10; i++) {
//            System.out.println(i);
//            playSound(Type.MOVE);
//            Thread.sleep(150);
//        }
    }
}