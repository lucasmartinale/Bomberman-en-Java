import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class ParedLadrillo extends Pared{
    public static BufferedImage[] destru_pared; //imagenes para la animacion de destruccion de pared
    protected Animacion anim_destru_pared;
    protected boolean pared_borrada;
    protected int contador_frames;
    
    public ParedLadrillo(double X, double Y){
        super("imagenes/escenario/pared_ladrillo.png", X, Y); 
        cargarImagenesPLadrillos();
        anim_destru_pared = new Animacion(90, destru_pared);
    }

    private void cargarImagenesPLadrillos(){
        destru_pared = new BufferedImage[6]; // Defino un array de imagenes

        destru_pared[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[0] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 1.png"));
        } catch (IOException e) {}

        destru_pared[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[1] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 2.png"));
        } catch (IOException e) {}
        
        destru_pared[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[2] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 3.png"));
        } catch (IOException e) {}

        destru_pared[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[3] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 4.png"));
        } catch (IOException e) {}

        destru_pared[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[4] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 5.png"));
        } catch (IOException e) {}

        destru_pared[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            destru_pared[5] = ImageIO.read(getClass().getResource("imagenes/escenario/pared_ladrillo 6.png"));
        } catch (IOException e) {}
    }
    
}
