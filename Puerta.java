import java.awt.*;
import java.awt.geom.*;

public class Puerta extends Objeto{
    private int puntoPuerta;
    private boolean flag = true;

    public Puerta(double X, double Y){
        super("imagenes/escenario/puerta.png");
        this.setPosicion(X, Y);
        this.puntoPuerta = 200;
    }

    public void drawPuerta(Graphics2D g){
        if(!Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30)) && this.flag){
            Escenario.cargarFantasmasAzules(this.getX(),this.getY());
            this.flag = false;
        }
        else{
            this.draw(g);
        }  
    }

    public void resetFlag(){
        this.flag = true;
    }

    public int getPuntoPuerta(){
        return this.puntoPuerta;
    }
    
}