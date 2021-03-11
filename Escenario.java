import java.awt.geom.*;
import java.awt.*;
import java.util.*;

public class Escenario {
    static protected LinkedList<ParedPiedra> paredP;
    static protected LinkedList<ParedLadrillo> paredL;
    static protected LinkedList<Bomba> bomba;
    static protected LinkedList<Fuego> fuego;
    static protected LinkedList<Fantasma> fantasmaRosa;
    static protected LinkedList<Fantasma> fantasmaAzul;
    static protected LinkedList<Bonus> bonus;
    static protected Puerta puerta;
    static protected Heroe heroe = new Heroe(); // Para que no se reinicien las vidas al pasar de nivel.
    static protected Escenario instancia = null;
    
    static protected Random r = new Random(); // Para la posicion aleatora de algunos objetos.

    static protected int cantidadBonus;
    static protected int bloquesX = 31; // Cantidad de bloques de ancho del escenario.
    static protected int bloquesY = 13; // Cantidad de bloques de alto del escenario.
    static protected int nivel = 1;
    static protected int cantidadLadrillos = 0;
    static protected int cantidadTotalLadrillos = 0;
    static protected int puntos = 0;;

    static protected Date dInit = new Date();
    static protected Date dAhora;
    static protected long timer = 203;

    static protected boolean siPuerta = false;
    static protected boolean PausarFantasmas = false;

    private Escenario(int cantFantasmas, int cantBonus, int porcParedL){
        paredP = new LinkedList<ParedPiedra>(); 
        paredL = new LinkedList<ParedLadrillo>();
        bomba = new LinkedList<Bomba>();
        fuego = new LinkedList<Fuego>();
        fantasmaRosa = new LinkedList<Fantasma>(); 
        fantasmaAzul = new LinkedList<Fantasma>();
        bonus = new LinkedList<Bonus>();
        puerta = new Puerta(-32,-32);

        cantidadBonus = cantBonus;
        
        heroe.setPosicion(64, 96);

        cargarParedesPiedra(bloquesX,bloquesY);
        cargarParedesLadrillo(bloquesX,bloquesY,porcParedL); // Ultima variable es porcentaje de llenado con ladrillos.
        cargarFantasmasRosas(bloquesX,bloquesY,cantFantasmas); // Ultima variable es cantidad de fantasmas.

        //Sonido.START_ESCENARIO.play();
        //Sonido.ESCENARIO.play(); 
    }

    public static Escenario getInstance(){
        if(instancia == null){
            instancia = new Escenario(6, 1, 1); // 6 fantasmas rosas, 1 bonus, 10% ladrillos (Nivel 1)
        }
        return instancia;
    }

    public static int getAncho(){
        return (bloquesX+1)*32;
    }

    private static void cargarParedesPiedra(int ancho, int alto){
        // Bordes superior e inferior
        for(int i=1; i<=ancho; i++){
            paredP.add(new ParedPiedra(i*32, 32+32)); 
            paredP.add(new ParedPiedra(i*32, 32*alto+32)); 
        }
        // Bordes laterales
        for(int i=2; i<alto; i++){
            paredP.add(new ParedPiedra(32, i*32+32)); 
            paredP.add(new ParedPiedra(ancho*32, i*32+32)); 
        }
        // Paredes interiores
        for(int i=3; i<alto; i++){
            for(int j=3; j<ancho; j++){
                if(i % 2 != 0 && j % 2 != 0)
                    paredP.add(new ParedPiedra(j*32,i*32+32));
            }
        }
    }

    private static void cargarParedesLadrillo(int ancho, int alto, int porcentaje){
        for(int i=2; i<alto; i++){
            for(int j=2; j<ancho; j++){
                if(!(i == 2 && j == 2) && !(i == 2 && j == 3) && !(i == 3 && j == 2) 
                && !(i % 2 != 0 && j % 2 != 0) && r.nextInt(10)<porcentaje)
                    paredL.add(new ParedLadrillo(j*32,i*32+32));
            }
        }
        cantidadLadrillos = paredL.size();
        cantidadTotalLadrillos = paredL.size();         
    }

    private static void cargarBonusPuerta(double posX, double posY, int bloquesX, int bloquesY){ 
        if(siPuerta == false && (cantidadLadrillos == (cantidadTotalLadrillos/2))){
            puerta.setPosicion(posX, posY);
            siPuerta = true;    
        }else{
            int k = r.nextInt(6);
            if(cantidadBonus > 0){
                if(r.nextInt(100) < 1 || (cantidadLadrillos == cantidadBonus)){
                    if(k == 0){
                        bonus.add(new BombaExtra(posX, posY));
                        cantidadBonus--;  
                    }
                    if(k == 1){
                        bonus.add(new Detonador(posX, posY));
                        cantidadBonus--; 
                    }
                    if(k == 2){
                        bonus.add(new MasVelocidad(posX, posY));
                        cantidadBonus--;
                    }
                    if(k == 3){
                        bonus.add(new PoderDeExplosion(posX, posY));
                        cantidadBonus--;
                    }
                    if(k == 4){
                        bonus.add(new Salto(posX, posY));
                        cantidadBonus--;
                    }
                    if(k == 5){
                        bonus.add(new VidaExtra(posX, posY));
                        cantidadBonus--;  
                    }
                }
            }
        }
    }
    
    private static void cargarFantasmasRosas(int ancho, int alto, int cantidad){
        int cant=0;
        while(cant<cantidad){
            for(int i=2; i<alto; i++){
                for(int j=2; j<ancho; j++){
                    if(!(i == 2 && j == 2) && !(i == 2 && j == 3) && !(i == 3 && j == 2) 
                    && !(i % 2 != 0 && j % 2 != 0) && cant < cantidad && r.nextInt(100)<3){
                        Rectangle2D n = new Rectangle2D.Double(j*32,i*32+32, 30, 30);
                        if(Escenario.colision(n)){
                            fantasmaRosa.add(new Fantasma(0, j*32, i*32+32));
                            cant++;
                        }
                    }
                }
            }
        }
    }

    public static void cargarFantasmasAzules(double posX, double posY){
        new Timer().schedule(new TimerTask() {
            int cant=0;
            @Override
            public void run() {
                while(cant<6){
                    fantasmaAzul.add(new Fantasma(1, posX, posY));
                    cant++;
                }
                puerta.resetFlag();
            }
        }, 500); // 1000 milisegundos = 1 segundo;
    }

    public static void nuevaBomba(double x, double y){
        bomba.add(new Bomba(x, y));
    }

    public static long timer(){
        dAhora= new Date();
    	long dateDiff = dAhora.getTime() - dInit.getTime();
        long diffSeconds = dateDiff / 1000;
        long seconds = timer - diffSeconds;
        return seconds;
    }

    public void draw(Graphics2D g){
        g.setColor(new Color(0, 180, 0));
        g.fillRect(0,0,(bloquesX+2)*32,(bloquesY+3)*32); // Fondo verde
        
        g.setColor(new Color(188, 188, 188));
        g.fillRect(0,0,(bloquesX+2)*32,64); // Panel gris superior

        puerta.drawPuerta(g);

        if(!bonus.isEmpty()){
            for(int i=0; i<bonus.size(); i++){
                bonus.get(i).drawBonus(g);
            }   
        } 
        for(int i=0; i<paredP.size(); i++){
            paredP.get(i).draw(g);
        }
        for(int i=0; i<paredL.size(); i++){ 
            if (paredL.get(i).pared_borrada == false){
                paredL.get(i).contador_frames = 0;
                paredL.get(i).draw(g);
            }else{
                if(paredL.get(i).contador_frames < 6){
                    try{
                        Thread.sleep(90);
                        paredL.get(i).anim_destru_pared.play();
                        paredL.get(i).imagen = paredL.get(i).anim_destru_pared.getFrameActual();
                        paredL.get(i).draw(g);
                    }catch (InterruptedException e) {}
                    paredL.get(i).contador_frames++;
                }else{
                    cargarBonusPuerta(paredL.get(i).getX(), paredL.get(i).getY(), bloquesX, bloquesY);
                    paredL.remove(i);
                    cantidadLadrillos--;
                }
            }
        }
        for(int i=0; i<bomba.size(); i++){
            bomba.get(i).imagen = bomba.get(i).anim_Bomba.getFrameActual();
            bomba.get(i).draw(g);
        }
        for(int i=0; i<fuego.size(); i++){
            fuego.get(i).draw(g);
        }
        for(int i=0; i<fantasmaRosa.size(); i++){
            fantasmaRosa.get(i).drawFantasma(g, 0, i);        
        }
        if(!fantasmaAzul.isEmpty()){
            for(int i=0; i<fantasmaAzul.size(); i++){
                fantasmaAzul.get(i).drawFantasma(g, 1, i);             
            }
        }

        heroe.drawHeroe(g);
    }

    public void update(){
        for(int i=0; i<bomba.size(); i++){
            bomba.get(i).anim_Bomba.play();
            if(bomba.get(i).explotar()){
                bomba.remove(i);
            }
        }

        for(int i=0; i<fuego.size(); i++){
            fuego.get(i).temporizador();
        }
        for(int i=0; i<fuego.size(); i++){
            if(fuego.get(i).desactivo()){
                fuego.remove(i);
            }
        }

        for(int i=0; i<fantasmaRosa.size(); i++){
            fantasmaRosa.get(i).movFantasma(0);
        }
        for(int i=0; i<fantasmaAzul.size(); i++){
            fantasmaAzul.get(i).movFantasma(1);
        }
    }
  
    public static boolean colision(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0, j=0;
        while(puede && i < paredP.size()){
            if(rPosicion.intersects((paredP.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }
        while(puede && j < paredL.size()){
            if(rPosicion.intersects(paredL.get(j).getObjeto().getBounds2D())){
                puede = false;
            }
            j++;
        }
        return puede;
    }

    public static boolean colision_puerta(Rectangle2D rPosicion){
        boolean puede = true;
        if(puede){
            if(rPosicion.intersects((puerta.getObjeto()).getBounds2D())){
                puede = false;
            }
        }
        return puede;
    }

    public static boolean colision_puerta_F(Rectangle2D rPosicion, int tipo, boolean a){
        boolean puede = true;
        int i=0, j=0;
        if(tipo == 1){
            while(puede && i < fantasmaAzul.size()){
                double diffX = fantasmaAzul.get(i).getX() - puerta.getX();
                double diffY = fantasmaAzul.get(i).getY() - puerta.getY();
                if(!(diffX >= -31 && diffX < 33 && diffY >= -31 && diffY <= 33) && fantasmaAzul.get(i).getAlejo()){ 
                    fantasmaAzul.get(i).setAlejo(false);
                }
                i++;
            }
            if(a == false && puede){
                if(rPosicion.intersects(new Rectangle2D.Double(puerta.getX(), puerta.getY(), 30, 30))){
                    puede = false;  
                }
            }
        }
        if(tipo == 0){
            while(puede && j < fantasmaRosa.size() ){
                if(fantasmaRosa.get(j).getAlejo() == true && puede){
                    if(rPosicion.intersects(new Rectangle2D.Double(puerta.getX(), puerta.getY(), 30, 30))){
                        puede = false;  
                    }
                j++;
                }
            }
        }
        return puede;
    }

    public static boolean colision_bonus(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0;
        while(puede && i < bonus.size()){
            if(rPosicion.intersects((bonus.get(i).getObjeto()).getBounds2D())){
                puede = false;
                getBonus(bonus.get(i).tipoBonus);
                //System.out.println(bonus.get(i).tipoBonus);
                bonus.remove(i);
            }
            i++;
        }
        return puede;
    }

    public static boolean colision_bonus_F(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0;
        while(puede && i < bonus.size()){
            if(rPosicion.intersects((bonus.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }
        return puede;
    }

    public static boolean colision_fantasma(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0, j=0;
        while(puede && i < fantasmaRosa.size()){
            if(rPosicion.intersects((fantasmaRosa.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }
        while(puede && j < fantasmaAzul.size()){
            if(rPosicion.intersects((fantasmaAzul.get(j).getObjeto()).getBounds2D())){
                puede = false;
            }
            j++;
        }
        return puede;
    }

    public static boolean colision_fuego(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0;
        while(puede && i < fuego.size()){
            if(rPosicion.intersects((fuego.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }
        return puede;
    }

    public static boolean colisiona_pared(Rectangle2D rPosicion){
        boolean puede = true, bandera = true;
        int j=0;
        while(puede && j < paredL.size() && bandera){
            if(rPosicion.intersects(paredL.get(j).getObjeto().getBounds2D())){
                puede = true;
                paredL.get(j).pared_borrada = true;
                bandera = false;
            }
            j++;
        }
        int i=0;
        while(puede && i < paredP.size() && bandera){
            if(rPosicion.intersects((paredP.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }

        // Fuera del mapa, para que el fuego del bonus Poder de Explosion no salga del mapa.
        int k=0;
        while(puede && k < bloquesX+2 && bandera){
            if(rPosicion.intersects((new Rectangle2D.Double(k*32, 32, 30, 30)).getBounds2D()) 
            || rPosicion.intersects((new Rectangle2D.Double(k*32, 32*(bloquesY+2), 30, 30)).getBounds2D())){
                puede = false;
            }
            k++;
        }
        int l=0;
        while(puede && l < bloquesY+2 && bandera){
            if(rPosicion.intersects((new Rectangle2D.Double(0, l*32, 30, 30)).getBounds2D()) 
            || rPosicion.intersects((new Rectangle2D.Double((bloquesX+1)*32, l*32, 30, 30)).getBounds2D())){
                puede = false;
            }
            l++;
        }
        
        return puede;
    }

    public static boolean colision_bomba(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0, j=0;
        if(!Salto.getSalto()){
            while(puede && i<bomba.size()){
                double diffX = heroe.getX() - bomba.get(i).getX();
                double diffY = heroe.getY() - bomba.get(i).getY();
                if(!(diffX >= -31 && diffX < 33 && diffY >= -31 && diffY <= 33) && bomba.get(i).getAlejo()){ 
                    bomba.get(i).setAlejo(false);
                }
                if(bomba.get(j).getAlejo() == false && puede){
                    if(rPosicion.intersects((bomba.get(i).getObjeto()).getBounds2D())){
                        puede = false;  
                    }
                j++;
                }
                i++;
            }
        }
        return puede;
    }

    public static boolean colision_bomba_F(Rectangle2D rPosicion){
        boolean puede = true;
        int i=0;
        while(puede && i < bomba.size()){
            if(rPosicion.intersects((bomba.get(i).getObjeto()).getBounds2D())){
                puede = false;
            }
            i++;
        }
        return puede;
    }

    public boolean gameOver(){
        return heroe.vidasRestantes()<0;
    }

    public boolean SubirDeNivel(){
        if(timer()>200 && !(heroe.vidasRestantes()<0) ){
            PausarFantasmas = true;
            return timer()>200;
        }
        if(timer()<=200 && !(heroe.vidasRestantes()<0)){
            PausarFantasmas = false;
            return false;
        }
        return false;
    }
    
    public boolean finJuego(){
        if(nivel == 3){
            return true;
        }
        else{
            return false;
        }
    }
    
    public static void subirNivel(){
        if(fantasmaRosa.isEmpty() && fantasmaAzul.isEmpty()){
            puntos += puerta.getPuntoPuerta();
            nivel++;
            //Sonido.ESCENARIO.stop();
            //Sonido.END_ESCENARIO.play();
            switch (nivel) {
                case 2: // 8 fantasmas rosas, 2 bonus, 20% ladrillos (Nivel 2)
                    dInit = new Date();
                    siPuerta=false;
                    instancia = new Escenario(6, 2, 2);
                    break;
                case 3:
                    dInit = new Date();
                    siPuerta=false;
                    instancia = new Escenario(6, 3, 3);
                    break;
                case 4:

                    break;
            }
        }  
    }

    public static void getBonus(int tipoBonus){
        puntos += bonus.get(0).getPuntoBonus();
        
        if(tipoBonus == 0){ // Bomba Extra
            heroe.masCantBombas();
        }
        if(tipoBonus == 1){ // Detonador
            Detonador.setDetonador(true);
        }
        if(tipoBonus == 2){ // Mas Velocidad
            heroe.masVelocidad();
        }
        if(tipoBonus == 3){ // Poder de Explosion
            PoderDeExplosion.setPD(true);
        }
        if(tipoBonus == 4){ // Salto
            Salto.setSalto(true);
        }
        if(tipoBonus == 5){ // Vida Extra
            heroe.agregarVida();
        }   
    }

}