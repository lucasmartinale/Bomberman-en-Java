public class MasVelocidad extends Bonus{
    
    public MasVelocidad(double X, double Y){
        super("imagenes/bonus/masVelocidad.png");
        this.setPosicion(X, Y);
        this.puntoBonus = 50;
        this.tipoBonus = 2;
    }
    
}