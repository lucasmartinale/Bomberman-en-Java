import java.awt.*;
import java.awt.geom.*;

public class Bonus extends Objeto{
    protected int puntoBonus;
    protected int tipoBonus;

    public Bonus(String img){
        super(img);
    }

    public void drawBonus(Graphics2D g){
        for(int i=0; i<Escenario.bonus.size(); i++){
            double posX = Escenario.bonus.get(i).getX();
            double posY = Escenario.bonus.get(i).getY();
            if(!Escenario.colision_fuego(new Rectangle2D.Double(posX, posY, 30, 30))){
                Escenario.bonus.remove(i);
                Escenario.cargarFantasmasAzules(posX,posY);
            }
            else{
                Escenario.bonus.get(i).draw(g);
            } 
        }   
    }

    public int getPuntoBonus(){
        return this.puntoBonus;
    }

}