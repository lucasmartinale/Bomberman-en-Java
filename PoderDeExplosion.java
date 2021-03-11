public class PoderDeExplosion extends Bonus{
    
    private static boolean activoPD = false;

    public PoderDeExplosion(double X, double Y){
        super("imagenes/bonus/poderDeExplosion.png");
        this.setPosicion(X, Y);
        this.puntoBonus = 50;
        this.tipoBonus = 3;
    }
    
    public static void setPD(boolean PD){
        activoPD = PD;
    }

    public static boolean getPD(){
        return activoPD;
    }
}