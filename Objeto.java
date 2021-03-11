import java.awt.*;
import java.awt.image.*; 
import javax.imageio.*; 
import java.io.IOException;
import java.awt.geom.*;

public class Objeto {
    private Point2D.Double posicion  = new Point2D.Double();
    protected BufferedImage imagen = null; 
    protected Escenario escenario;
    protected double x=100;
    protected double y=100;

    public Objeto(String img){
        try{
            imagen = ImageIO.read(getClass().getResource(img));
        }catch(IOException e){
            System.out.println("Error al cargar la imagen:");
            System.out.println(img+"+>"+this.getClass().getResource(img));
        }
    }

    public void setX(double x){
        posicion.x = x;
    }

    public void setY(double y){
        posicion.y = y;
    }
    
    public double getX(){
        return posicion.getX(); 
    }

    public double getY(){
        return posicion.getY(); 
    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x, y);
    }

    public void draw(Graphics2D g){
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }

    // Genera cuadrado para detectar colision
    public Rectangle2D getObjeto(){
        return new Rectangle2D.Double(posicion.getX(), posicion.getY(), 30, 30);
    }

    public boolean posicion_validaX_Heroe(double delta){
        
        boolean esValido = true;
        if((delta > 1500) || (delta < 0) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(delta, this.getY(), 30, 30);
        if(!Escenario.colision(n)){
            esValido = false;
        }
        if(!Escenario.colision_bonus(n)){
            esValido = false;
        }
        if(!Escenario.colision_bomba(n)){
            esValido = false;
        }
        if(!Escenario.colision_puerta(n) && !Escenario.fantasmaRosa.isEmpty()){
            esValido = false;
        }
        if(!Escenario.colision_puerta(n) && !Escenario.fantasmaAzul.isEmpty()){
            esValido = false;
        }
        return esValido;
    }
     
    public boolean posicion_validaY_Heroe(double delta){
        boolean esValido = true;
        if((delta > 600) || (delta < 15) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(this.getX(), delta, 30, 30);
        if(!Escenario.colision(n)){
            esValido = false;
        }
        if(!Escenario.colision_bonus(n)){
            esValido = false;
        }
        if(!Escenario.colision_bomba(n)){
            esValido = false;
        }
        if(!Escenario.colision_puerta(n) && !Escenario.fantasmaRosa.isEmpty()){
            esValido = false;
        }
        if(!Escenario.colision_puerta(n) && !Escenario.fantasmaAzul.isEmpty()){
            esValido = false;
        }
        return esValido;
    }

    public boolean posicion_validaX_Fantasma(double delta, int tipo, boolean a){
        
        boolean esValido = true;
        if((delta > 1000) || (delta < 0) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(delta, this.getY(), 30, 30);
        if(!Escenario.colision(n)){
            esValido = false;
        }
        if(!Escenario.colision_puerta_F(n,tipo,a)){
            esValido = false;
        }
        if(!Escenario.colision_bonus_F(n)){
            esValido = false;
        }
        if(!Escenario.colision_bomba_F(n)){
            esValido = false;
        }
        if(Escenario.PausarFantasmas == true){
            esValido = false;
        }
        return esValido;
    }
     
    public boolean posicion_validaY_Fantasma(double delta, int tipo, boolean a){
        boolean esValido = true;
        if((delta > 600) || (delta < 15) ){
            esValido = false;
        }
        Rectangle2D n = new Rectangle2D.Double(this.getX(), delta, 30, 30);
        if(!Escenario.colision(n)){
            esValido = false;
        }
        if(!Escenario.colision_puerta_F(n,tipo,a)){
            esValido = false;
        }
        if(!Escenario.colision_bonus_F(n)){
            esValido = false;
        }
        if(!Escenario.colision_bomba_F(n)){
            esValido = false;
        }
        if(Escenario.PausarFantasmas == true){
            esValido = false;
        }
        return esValido;
    }
}
