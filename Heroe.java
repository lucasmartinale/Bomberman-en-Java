import java.awt.geom.*;
import java.awt.*;
import com.entropyinteractive.*;
import java.awt.event.*;
import java.awt.image.*; 
import javax.imageio.*; 
import java.io.IOException;
import java.util.*;

public class Heroe extends Personaje {
    private int vidas;
    private double DESPLAZAMIENTO; // Velocidad del heroe.
    public static BufferedImage[] caminarizq, caminarder, caminararriba, caminarabajo, muerte_heroe;
    private int moviendo_hacia = 0; // 0 significa no moviendose 1=izq 2=der 3=arriba 4=abajo
    private int cantBombas = 0, contador_frames;
    protected static boolean detonador = false;
    private boolean bandera = true;

    private Animacion animCaminarIzq, animCaminarDer, animCaminarArriba, animCaminarAbajo, animMuerte;

    final int[] arr = { 64, 96, 128, 160, 192, 224, 256, 288, 320, 352, 384, 416, 448, 480, 512, 544, 576, 608, 640,
            672, 704, 736, 768, 800, 832, 864, 896, 928, 960, 992 };

    public Heroe() {
        super("");
        this.vivo = true;
        this.vidas = 3;
        this.DESPLAZAMIENTO = 100.00;

        cargarImagenesHeroe();

        // Animaciones
        animCaminarIzq = new Animacion(200, caminarizq);
        animCaminarDer = new Animacion(200, caminarder);
        animCaminarArriba = new Animacion(200, caminararriba);
        animCaminarAbajo = new Animacion(200, caminarabajo);
        animMuerte = new Animacion(200, muerte_heroe);
    }

    public void moverHeroe(double delta, Keyboard keyboard) {
        if(this.vivo){
            if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                if (this.posicion_validaY_Heroe(this.getY() - DESPLAZAMIENTO * delta)) {
                    moviendo_hacia = 3;
                    animCaminarArriba.play();
                    
                    this.setY(this.getY() - DESPLAZAMIENTO * delta);
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                if (this.posicion_validaY_Heroe(this.getY() + DESPLAZAMIENTO * delta)) {
                    moviendo_hacia = 4;
                    animCaminarAbajo.play();
                    
                    this.setY(this.getY() + DESPLAZAMIENTO * delta);
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                if (this.posicion_validaX_Heroe(this.getX() - DESPLAZAMIENTO * delta)) {
                    moviendo_hacia = 1;
                    animCaminarIzq.play();
                    
                    this.setX(this.getX() - DESPLAZAMIENTO * delta);
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                if (this.posicion_validaX_Heroe(this.getX() + DESPLAZAMIENTO * delta)) {
                    moviendo_hacia = 2;
                    animCaminarDer.play();
                    
                    this.setX(this.getX() + DESPLAZAMIENTO * delta);
                }
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_X) && bandera) {
                this.soltarBomba();
                detonador=false;
                this.bandera = false;
                new Timer().schedule(new TimerTask() {
                    public void run() {
                    bandera = true;
                    }
                }, 500);
            }

            if (keyboard.isKeyPressed(KeyEvent.VK_Z) && Detonador.getDetonador() == true) {
                detonador = true;
            }
        }
    }

    public void soltarBomba() {
        int i = 0, j = 0;
        while (arr[i] < (int) this.getX() + 32) {
            x = arr[i];
            i++;
        }
        while (arr[j] < (int) this.getY() + 32) {
            y = arr[j];
            j++;
        }

        if(this.entreBombas()) {
            Rectangle2D r = new Rectangle2D.Double(x, y, 30, 30);
            if (Escenario.colision(r)) {
                Escenario.nuevaBomba(x, y);
                //Sonido.BOMBA.play();
            }  
        }  
    }

    public void drawHeroe(Graphics2D g) {
        if(!Escenario.colision_fantasma(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))
        || !Escenario.colision_fuego(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))
        || Escenario.timer() == 0) {
            this.vivo = false;
            if(this.vidasRestantes()>=0){
                //Sonido.ESCENARIO.stop();
                //Sonido.MUERTE.play();
            }
        }

        if(this.vivo == true){
            contador_frames = 0;
            this.imagen = getFrameDeAnimacionActual();
            this.draw(g);
            if(!Escenario.colision_puerta(new Rectangle2D.Double(this.getX(), this.getY(), 30, 30))){
                Escenario.subirNivel();
            }
        }else{
            try{
                Thread.sleep(200);
                this.imagen = animMuerte.getFrameActual();
                animMuerte.play();
                this.draw(g);
            }catch (InterruptedException e) {} 
            this.contador_frames++;
            if(contador_frames == 7){
                this.vidas--;
                this.vivo = true;
                this.setPosicion(64, 96);
                Escenario.dInit = new Date();
                if(this.vidasRestantes()>=0){
                    //Sonido.START_ESCENARIO.play();
                    //Sonido.ESCENARIO.play();
                }
                this.contador_frames = 0;

                if(this.vidas>0){
                    Juego_Bomberman.pantallaNegra(g, 0); // Pantalla negra muere heroe
                }
            }
        }
    }

    private BufferedImage getFrameDeAnimacionActual() {
        //0 significa no moviendose 1=izq 2=der 3=arriba 4=abajo
        switch(this.moviendo_hacia){
            case 1: return animCaminarIzq.getFrameActual();
            case 2: return animCaminarDer.getFrameActual();
            case 3: return animCaminarArriba.getFrameActual();
            case 4: return animCaminarAbajo.getFrameActual();
        }
        return animCaminarAbajo.getFrameActual(); 
    }

    public boolean entreBombas() {
        return !(Escenario.bomba.size() > this.cantBombas);
    }

    public void masCantBombas(){
        this.cantBombas++;
    }

    public int vidasRestantes() {
        return this.vidas-1;
    }

    public void agregarVida(){
        this.vidas++;
    }

    public void masVelocidad(){
        this.DESPLAZAMIENTO += 25.00;
    }

    public void cargarImagenesHeroe() {
        caminarizq = new BufferedImage[3]; // Defino un array de imagenes

        // Añado imagen de heroe caminando a la izquierda1
        caminarizq[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarizq[0] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_izq1.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la izquierda2
        caminarizq[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarizq[1] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_izq2.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la izquierda3
        caminarizq[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarizq[2] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_izq3.png"));
        } catch (IOException e) {}

        caminarder = new BufferedImage[3]; // definimos array para caminar hacia la derecha

        // Añado imagen de heroe caminando a la derecha1
        caminarder[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarder[0] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_der1.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la derecha2
        caminarder[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarder[1] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_der2.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la derecha3
        caminarder[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarder[2] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_der3.png"));
        } catch (IOException e) {}

        caminararriba = new BufferedImage[3]; // definimos array para caminar hacia la derecha

        // Añado imagen de heroe caminando a la arriba1
        caminararriba[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminararriba[0] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_arriba1.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la arriba2
        caminararriba[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminararriba[1] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_arriba2.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la arriba3
        caminararriba[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminararriba[2] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_arriba3.png"));
        } catch (IOException e) {}

        caminarabajo = new BufferedImage[3]; // definimos array para caminar hacia la derecha

        // Añado imagen de heroe caminando a la abajo1
        caminarabajo[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarabajo[0] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_abajo1.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la abajo2
        caminarabajo[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarabajo[1] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_abajo2.png"));
        } catch (IOException e) {}

        // Añado imagen de heroe caminando a la abajo3
        caminarabajo[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            caminarabajo[2] = ImageIO.read(getClass().getResource("/imagenes/heroe/heroe_abajo3.png"));
        } catch (IOException e) {}

        muerte_heroe = new BufferedImage[7];

        muerte_heroe[0] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[0] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe1.png"));
        } catch (IOException e) {}

        muerte_heroe[1] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[1] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe2.png"));
        } catch (IOException e) {}

        muerte_heroe[2] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[2] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe3.png"));
        } catch (IOException e) {}

        muerte_heroe[3] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[3] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe4.png"));
        } catch (IOException e) {}

        muerte_heroe[4] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[4] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe5.png"));
        } catch (IOException e) {}

        muerte_heroe[5] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[5] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe6.png"));
        } catch (IOException e) {}

        muerte_heroe[6] = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        try {
            muerte_heroe[6] = ImageIO.read(getClass().getResource("/imagenes/heroe/muerte_heroe7.png"));
        } catch (IOException e) {}
    }
}