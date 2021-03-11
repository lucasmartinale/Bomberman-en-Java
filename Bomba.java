import java.awt.geom.*;
import java.awt.image.*; 
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bomba extends Objeto{
    private int tiempo;
    public static BufferedImage[] animBomba; //Para la animacion de la bomba
    protected Animacion anim_Bomba; 
    private boolean alejo;
 
    public Bomba(double x, double y){
        super("");
        cargarImagenesBomba();
        anim_Bomba = new Animacion(200, animBomba); 
        this.setPosicion(x, y);
        this.tiempo = 80;
        this.alejo = true;
    }

    public boolean explotar(){
        if(Detonador.getDetonador() == false){
            if(tiempo > 0 && Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))){
                tiempo--;
                return false;
            }
            else{
                explosion();
                return true;
            }
        }
        else{
            if(Heroe.detonador == false && Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))){
                return false;
            }
            else{
                explosion();
                return true;
            }
        }
    }

    public void explosion(){
        //Sonido.EXPLOSION.play();
        if(PoderDeExplosion.getPD() == false){
            Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion1.png",this.getX(), this.getY()));
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()+32, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion3.png",this.getX()+32, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()-32, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion7.png",this.getX()-32, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()-32, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion5.png",this.getX(), this.getY()-32));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()+32, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion9.png",this.getX(), this.getY()+32));
            }
        }
        if(PoderDeExplosion.getPD() == true){
            Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion1.png",this.getX(), this.getY()));
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()+32, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion2.png",this.getX()+32, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()+64, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion3.png",this.getX()+64, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()-32, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion6.png",this.getX()-32, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX()-64, this.getY(), 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion7.png",this.getX()-64, this.getY()));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()-32, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion4.png",this.getX(), this.getY()-32));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()-64, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion5.png",this.getX(), this.getY()-64));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()+32, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion8.png",this.getX(), this.getY()+32));
            }
            if(Escenario.colisiona_pared(new Rectangle2D.Double(this.getX(), this.getY()+64, 32, 32))){
                Escenario.fuego.add(new Fuego("/imagenes/explosiones/explosion9.png",this.getX(), this.getY()+64));
            }
        }
    }

    public void setAlejo(boolean alejo){
        this.alejo = alejo;
    }

    public boolean getAlejo(){
        return this.alejo;
    }

    public void cargarImagenesBomba() {
        animBomba = new BufferedImage[3]; // Defino un array de imagenes

        animBomba[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            animBomba[0] = ImageIO.read(getClass().getResource("/imagenes/bombas/bomba1.png"));
        } catch (IOException e) {}
 
        animBomba[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            animBomba[1] = ImageIO.read(getClass().getResource("/imagenes/bombas/bomba2.png"));
        } catch (IOException e) {}

        animBomba[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            animBomba[2] = ImageIO.read(getClass().getResource("/imagenes/bombas/bomba3.png"));
        } catch (IOException e) {}    
    }
}