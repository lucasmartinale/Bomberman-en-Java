import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

public enum Sonido {
   
   ESCENARIO("Stage.wav"), 
   START_ESCENARIO("Stage start.wav"),
   END_ESCENARIO("Stage end.wav"),
   BOMBA("Poner bomba.wav"), 
   EXPLOSION("Boom.wav"), 
   GAME_OVER("Game Over.wav"), 
   INTERFAZ("Menu.wav"),
   MUERTE("Muerte.wav"),
   GANAR("Ganar.wav"); 

   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   private Clip clip;

   Sonido(String wav) {
      try {
         URL url = this.getClass().getClassLoader().getResource("sonidos/"+wav);

         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

         clip = AudioSystem.getClip();

         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         //e.printStackTrace();
      } catch (IOException e) {
         //e.printStackTrace();
      } catch (LineUnavailableException e) {
         //e.printStackTrace();
      }
   }

   public void play() {
      if (volume != Volume.MUTE) {
         if (!clip.isRunning()){
         	  clip.setFramePosition(0);
         		clip.start();
         }
      }
   }

   public void stop(){
      if (clip.isRunning()){
         clip.stop();
      }
   }

   static void init() {
      values();
   }
}