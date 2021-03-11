public class Detonador extends Bonus{
    private static boolean detonador = false;
    
    public Detonador(double X, double Y){
        super("imagenes/bonus/detonador.png");
        this.setPosicion(X, Y);
        this.puntoBonus = 50;
        this.tipoBonus = 1;
    }

    public static void setDetonador(boolean d){
        detonador = d;
    }

    public static boolean getDetonador(){
        return detonador;
    }
    
}