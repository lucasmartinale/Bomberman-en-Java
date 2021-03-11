import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Fantasma extends Personaje {
    private int puntoFantasma;
    private int Rosa = 0, Azul = 1;
    private double delta = 0.02;
    private int direccion;
    public static BufferedImage[] caminar_FR, muerte_FR, caminar_FA, muerte_FA; // Para la animacion del Fantasma
    private double DESPLAZAMIENTO = 50.00; // Velocidad del fantasma.
    private int num_iteracion_anim;
    private boolean alejo;

    private Animacion animCaminar_FR, animMuerte_FR; // FR Fantasma Rosa
    private Animacion animCaminar_FA, animMuerte_FA; // FA Fantasma Azul

    public Fantasma(int color, double x, double y) {
        super("");
        cargarImagenesFantasma();
        animCaminar_FR = new Animacion(200, caminar_FR);
        animCaminar_FA = new Animacion(200, caminar_FA);
        animMuerte_FR = new Animacion(90, muerte_FR);
        animMuerte_FA = new Animacion(90, muerte_FA);
        if (color == Rosa) {
            animCaminar_FR.play();
            imagen = animCaminar_FR.getFrameActual();
            this.puntoFantasma = 100;
        }
        if (color == Azul) {
            animCaminar_FA.play();
            imagen = animCaminar_FA.getFrameActual();
            this.puntoFantasma = 150;
        }
        this.alejo = true;
        this.setPosicion(x, y);
        this.direccion = ThreadLocalRandom.current().nextInt(1, 5);
        this.vivo = true;
    }

    public void drawFantasma(Graphics2D g, int color, int i) {
        
        if (this.vivo == true) {
            num_iteracion_anim = 0;
            if (color == Rosa) {
                if (!Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))) {
                    this.vivo = false;
                    Escenario.puntos += this.puntoFantasma;
                } else {
                    imagen = animCaminar_FR.getFrameActual();
                    this.draw(g);
                }
            }
            if (color == Azul) {
                if (!Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))){
                    this.vivo = false;
                    Escenario.puntos += this.puntoFantasma;
                } else {
                    imagen = animCaminar_FA.getFrameActual();
                    this.draw(g);
                }
            }
        } else {
            if (color == Rosa) {
                try{
                    Thread.sleep(90);
                    this.imagen = animMuerte_FR.getFrameActual();
                    animMuerte_FR.play();
                    this.draw(g);
                }catch(InterruptedException e){}
                this.num_iteracion_anim++;
                if (this.num_iteracion_anim == 7) {
                    Escenario.fantasmaRosa.remove(i);
                }
            }
            if (color == Azul) {
                try{
                    Thread.sleep(90);
                    this.imagen = animMuerte_FA.getFrameActual();
                    animMuerte_FA.play();
                    this.draw(g);
                }catch(InterruptedException e){}
                this.num_iteracion_anim++;
                if (this.num_iteracion_anim == 7) {
                    Escenario.fantasmaAzul.remove(i);
                }
            }
        }
    }

    public void movFantasma(int color) {
        switch (this.direccion) {
            case 1: {
                if (this.posicion_validaY_Fantasma(this.getY() - DESPLAZAMIENTO * delta,color,alejo) ){
                    this.setY(this.getY() - DESPLAZAMIENTO * delta);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                } else {
                    this.direccion = ThreadLocalRandom.current().nextInt(1, 5);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                }

            }
                break;
            case 2: {
                if (this.posicion_validaY_Fantasma(this.getY() + DESPLAZAMIENTO * delta,color,alejo)) {
                    this.setY(this.getY() + DESPLAZAMIENTO * delta);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                } else {
                    this.direccion = ThreadLocalRandom.current().nextInt(1, 5);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                }
            }
                break;
            case 3: {
                if (this.posicion_validaX_Fantasma(this.getX() - DESPLAZAMIENTO * delta,color,alejo)) {
                    this.setX(this.getX() - DESPLAZAMIENTO * delta);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                } else {
                    this.direccion = ThreadLocalRandom.current().nextInt(1, 5);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                }
            }
                break;
            case 4: {
                if (this.posicion_validaX_Fantasma(this.getX() + DESPLAZAMIENTO * delta,color,alejo)) {
                    this.setX(this.getX() + DESPLAZAMIENTO * delta);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                } else {
                    this.direccion = ThreadLocalRandom.current().nextInt(1, 5);
                    if (color == Rosa)
                        animCaminar_FR.play();
                    if (color == Azul)
                        animCaminar_FA.play();
                }
            }
                break;
        }
    }

    public void setAlejo(boolean alejo){
        this.alejo = alejo;
    }

    public boolean getAlejo(){
        return this.alejo;
    }

    public void cargarImagenesFantasma() {
        caminar_FR = new BufferedImage[6]; // Defino un array de imagenes

        caminar_FR[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[0] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando1.png"));
        } catch (IOException e) {
        }

        caminar_FR[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[1] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando2.png"));
        } catch (IOException e) {
        }

        caminar_FR[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[2] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando3.png"));
        } catch (IOException e) {
        }

        caminar_FR[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[3] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando4.png"));
        } catch (IOException e) {
        }

        caminar_FR[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[4] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando5.png"));
        } catch (IOException e) {
        }

        caminar_FR[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FR[5] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/caminando6.png"));
        } catch (IOException e) {
        }

        muerte_FR = new BufferedImage[6]; // Defino un array de imagenes

        muerte_FR[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[0] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/muerte1.png"));
        } catch (IOException e) {
        }

        muerte_FR[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[1] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/muerte2.png"));
        } catch (IOException e) {
        }

        muerte_FR[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[2] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/muerte3.png"));
        } catch (IOException e) {
        }

        muerte_FR[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[3] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/muerte4.png"));
        } catch (IOException e) {
        }

        muerte_FR[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[4] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Rosa/muerte5.png"));
        } catch (IOException e) {
        }

        muerte_FR[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FR[5] = ImageIO.read(getClass().getResource("imagenes/puntaje/100.png"));
        } catch (IOException e) {
        }

       
       
        caminar_FA = new BufferedImage[6]; // Defino un array de imagenes

        caminar_FA[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[0] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando1.png"));
        } catch (IOException e) {
        }

        caminar_FA[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[1] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando2.png"));
        } catch (IOException e) {
        }

        caminar_FA[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[2] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando3.png"));
        } catch (IOException e) {
        }

        caminar_FA[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[3] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando4.png"));
        } catch (IOException e) {
        }

        caminar_FA[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[4] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando5.png"));
        } catch (IOException e) {
        }

        caminar_FA[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminar_FA[5] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/caminando6.png"));
        } catch (IOException e) {
        }

        muerte_FA = new BufferedImage[6]; // Defino un array de imagenes

        muerte_FA[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[0] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/muerte1.png"));
        } catch (IOException e) {
        }

        muerte_FA[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[1] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/muerte2.png"));
        } catch (IOException e) {
        }

        muerte_FA[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[2] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/muerte3.png"));
        } catch (IOException e) {
        }

        muerte_FA[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[3] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/muerte4.png"));
        } catch (IOException e) {
        }

        muerte_FA[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[4] = ImageIO.read(getClass().getResource("imagenes/enemigos/Fantasma Azul/muerte5.png"));
        } catch (IOException e) {
        }

        muerte_FA[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_FA[5] = ImageIO.read(getClass().getResource("imagenes/puntaje/150.png"));
        } catch (IOException e) {
        }
    }
}