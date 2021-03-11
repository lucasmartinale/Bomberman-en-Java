import java.awt.image.*;

public class Animacion {
    private int indice, velocidad;
    private long ultimo_tiempo, timer;
    private BufferedImage[] frames;

    public Animacion(int velocidad, BufferedImage[] frames){
        this.velocidad = velocidad;
        this.frames = frames;
        indice = 0;
        timer = 0;
        ultimo_tiempo = System.currentTimeMillis(); //hora actual en milisegundos
    }
    
    public void play(){
        timer += System.currentTimeMillis() - ultimo_tiempo;
        ultimo_tiempo = System.currentTimeMillis();
        
        if(timer > velocidad){
            indice++;
            timer = 0;
            if(indice >= frames.length){
                indice = 0;
            }
        }
    }

    public void play_2(){ //play_2 es una reproduccion no ciclica
        timer += System.currentTimeMillis() - ultimo_tiempo;
        ultimo_tiempo = System.currentTimeMillis();
        
        if(timer > velocidad){
            indice++;
            timer = 0;
        }
    }

    public BufferedImage getFrameActual(){
        return frames[indice];
    }

}