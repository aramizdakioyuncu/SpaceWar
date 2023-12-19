package Spaceboom.Utility;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class SoundPlayer {

    public static void playAsync(String sesDosyaYolu) {
        new Thread(() -> {
            try {
                // Ses dosyasını yükleyerek bir AudioInputStream oluşturun
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(SoundPlayer.class.getResource("/sound/" + sesDosyaYolu));

                // Ses çalma işlemi için bir Clip oluşturun
                Clip clip = AudioSystem.getClip();

                // Clip'e AudioInputStream'i ekleyin
                clip.open(audioInputStream);

                // Ses dosyasını çal
                clip.start();

                // Ses çalma işlemi tamamlandığında programı kapatmak için bekleme ekleyebilirsiniz
                Thread.sleep(clip.getMicrosecondLength() / 1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
