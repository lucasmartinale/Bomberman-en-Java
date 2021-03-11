public class Salto extends Bonus{
    private static boolean salto = false;
    
    public Salto(double X, double Y){
        super("imagenes/bonus/salto.png");
        this.setPosicion(X, Y);
        this.puntoBonus = 50;
        this.tipoBonus = 4;
    }

    public static void setSalto(boolean s){
        salto = s;
    }

    public static boolean getSalto(){
        return salto;
    }

}