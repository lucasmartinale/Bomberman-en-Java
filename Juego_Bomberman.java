import com.entropyinteractive.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Juego_Bomberman extends JGame {

    Escenario escenario;
    static Camara cam;
    
    public static void main(String[] args) {
        Juego_Bomberman game = new Juego_Bomberman();
        game.run(1.0 / 30.0);
        Jugador j = new Jugador();
    }

    public Juego_Bomberman() {
        super("Bomberman", 640, 480);
        System.out.println(appProperties.stringPropertyNames());
    }

    public void gameStartup() {
        try{
            this.escenario = Escenario.getInstance();
            cam = new Camara(0,0);
            cam.setRegionVisible(640,480);
        }
        catch(Exception e){}  
    }

    public void gameUpdate(double delta) {
        Keyboard keyboard = this.getKeyboard();
        
        // Esc fin del juego
        LinkedList < KeyEvent > keyEvents = keyboard.getEvents();
        for (KeyEvent event: keyEvents) {
            if ((event.getID() == KeyEvent.KEY_PRESSED) &&
                (event.getKeyCode() == KeyEvent.VK_ESCAPE)) {
                stop();
                //Sonido.ESCENARIO.stop();
                Jugador.puntaje=Escenario.puntos;
            }
        }

        Escenario.heroe.moverHeroe(delta, keyboard);
        cam.seguirPersonaje(Escenario.heroe); // La camara sigue al Personaje.
        escenario.update();
    }

    public void gameDraw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.translate(cam.getX(),cam.getY());

        escenario.draw(g);

        g.translate(-cam.getX(),-cam.getY());

        g.setFont(new Font("Retro Gaming", 3, 26));
        g.setColor(Color.black);
        g.drawString("TIME "+Escenario.timer(),30,58); // Timer
        g.drawString(""+Escenario.puntos, 382,58); // Puntaje
        g.drawString("LEFT  "+Escenario.heroe.vidasRestantes(),498,58); // Cantidad de vidas restantes

        g.setFont(new Font("Retro Gaming", 3, 26));
    	g.setColor(Color.white);
        g.drawString("TIME "+Escenario.timer(),28,56); // Timer
        g.drawString(""+Escenario.puntos,380,56); // Puntaje
        g.drawString("LEFT  "+Escenario.heroe.vidasRestantes(),496,56); // Cantidad de vidas restantes

        if(escenario.finJuego()){
            //Sonido.ESCENARIO.stop();
            //Sonido.GANAR.play();
            pantallaNegra(g,2);
            new Timer().schedule(new TimerTask() {
                public void run() {
                    stop();
                }
            }, 3000);
        }

        if(escenario.gameOver()){
            //Sonido.ESCENARIO.stop();
            //Sonido.GAME_OVER.play();
            pantallaNegra(g,0);
            new Timer().schedule(new TimerTask() {
                public void run() {
                    stop();
                }
            }, 3000);
        }

        if(escenario.SubirDeNivel() && !escenario.finJuego()){
            pantallaNegra(g,1);
        }
    }

    public void gameShutdown() {
       Log.info(getClass().getSimpleName(), "Cerrando el juego.");
    }

    public  static void pantallaNegra(Graphics2D g, int tipo){
        if(tipo==0){
            cam.setRegionVisible(640,480);
            cam.setY(0);
            g.setColor(Color.black);
            g.fillRect(0,0,700,500); 
            g.setColor(Color.WHITE);
            g.setFont(new Font("Retro Gaming", 3, 26));
            g.drawString("GAME OVER ",250,260);
            cam.setX(0);
            Jugador.puntaje=Escenario.puntos;
        }
        if(tipo==1){
            cam.setRegionVisible(640,480);
            cam.setY(0);
            g.setColor(Color.black);
            g.fillRect(0,0,700,500); 
            g.setColor(Color.WHITE);
            g.setFont(new Font("Retro Gaming", 3, 26));
            g.drawString("Stage  "+Escenario.nivel,250,260);
            cam.setX(0);
    
        }
        if(tipo==2){
            cam.setRegionVisible(640,480);
            cam.setY(0);
            g.setColor(Color.black);
            g.fillRect(0,0,700,500); 
            g.setColor(Color.WHITE);
            g.setFont(new Font("Retro Gaming", 3, 26));
            g.drawString("You win!!!",250,260);
            cam.setX(0);
            Jugador.puntaje = Escenario.puntos;
        }
    }
    
}

