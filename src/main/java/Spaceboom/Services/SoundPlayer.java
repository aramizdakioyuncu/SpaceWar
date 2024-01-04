package Spaceboom.Services;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.Objects;

public class SoundPlayer {

    private Clip clip;
    private boolean repeat;

    public SoundPlayer() {
        try {

            this.clip = AudioSystem.getClip();
            this.repeat = false;
        }catch (Exception ignored) {

        }


    }

    public void playAsync(String sesDosyaYolu) {
        new Thread(() -> {
            try {
                clip = AudioSystem.getClip();
                if (clip.isRunning()) {
                    clip.stop();
                }


                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource("/sound/" + sesDosyaYolu)));


                clip.open(audioInputStream);


                clip.start();


                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        if (repeat) {
                            clip.setMicrosecondPosition(0);
                            clip.start();
                        } else {
                            clip.stop();
                        }
                    }
                });

            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }


    public  void StopMusic() {
        new Thread(() -> {
            try {

                repeat = false;
                clip.stop();

            } catch (Exception e) {
                System.out.println(e);
            }
        }).start();
    }


    public void RepeatMusic(boolean repeat){

       this.repeat = repeat;

    }

}
