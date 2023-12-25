package Spaceboom.Utility;

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
            // Ses çalma işlemi için bir Clip oluşturun
            this.clip = AudioSystem.getClip();
            this.repeat = false;
        }catch (Exception ignored) {

        }


    }

    public void playAsync(String sesDosyaYolu) {

        new Thread(() -> {

            try {
                // Ses dosyasını yükleyerek bir AudioInputStream oluşturun
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource("/sound/" + sesDosyaYolu)));

                // Clip'e AudioInputStream'i ekleyin
                clip.open(audioInputStream);

                // Ses dosyasını çal
                clip.start();

                // Playback süresi tamamlandığında programı kapatmak için bekleme ekleyebilirsiniz
                // Ancak daha güvenilir bir yöntem, çalma süresi bittiğinde bir olay dinleyicisi eklemektir
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        if (repeat ) {
                            clip.setMicrosecondPosition(0);
                            clip.start();
                        }
                    }
                });

            } catch (Exception e) {
                System.out.println("Müzik Dosyası Bulunamadı : " + sesDosyaYolu);
            }
        }).start();
    }


    public  void StopMusic() {
        new Thread(() -> {
            try {
                // Ses dosyasını çalma
                repeat = false;
                clip.stop();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void RepeatMusic(boolean repeat){

       this.repeat = repeat;

    }

}
