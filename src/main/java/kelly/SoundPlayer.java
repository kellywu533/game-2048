package kelly;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SoundPlayer {
    private static final String AUDIO_DIR = "/System/Library/Sounds/";
    public enum Type {
        MOVE("Ping.aiff")
        , ILLEGAL("Basso.aiff")
        , CLICK("Bottle.aiff")
        , GAME_OVER("Sosumi.aiff")
        ;

        byte[] soundData;
        Type(String fname) {
            try {
                Path path = Paths.get(AUDIO_DIR, fname);
                soundData = Files.readAllBytes(path);
            } catch (IOException e) {
                System.out.println("unable to load sound clip " + fname);
            }
        }

        public InputStream soundDataInputStream() {
            if(soundData == null) {
                return null;
            }
            return new ByteArrayInputStream(soundData);
        }
    }

    public static void playSound(InputStream is) {
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(is)) {
            final Clip clip = AudioSystem.getClip();
            clip.addLineListener((event) -> {
                    LineEvent.Type type = event.getType();
                    if (type == LineEvent.Type.OPEN) {
//                        System.out.println("OPEN :"
////                                + clip + " - " + ais
//                        );
                    } else if (type == LineEvent.Type.CLOSE) {
//                        System.out.println("CLOSE:"
////                                + clip + " - " + ais
//                        );
                    } else if (type == LineEvent.Type.START) {
//                        System.out.println("START:"
////                                + clip + " - " + ais
//                        );
                    } else if (type == LineEvent.Type.STOP) {
//                        System.out.println("STOP :"
////                                + clip + " - " + ais
//                        );
                        clip.close();
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
        if(t.soundDataInputStream() == null) {
            System.out.println("no sound " + t);
            return;
        }
        playSound(t.soundDataInputStream());
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<10; i++) {
            System.out.println(i);
            playSound(Type.MOVE);
            Thread.sleep(150);
        }
    }
}