import java.awt.image.*;

public class Personaje extends Objeto{
    protected boolean vivo;  

    public Personaje(String img){
        super(img);
    }

    public void setImagen(BufferedImage img){
        this.imagen = img;
    }

    public void update(double delta){ 
        
    }
}