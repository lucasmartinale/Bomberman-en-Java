public class Fuego extends Objeto {
    protected int tiempo;
    protected boolean desactivado;

    public Fuego(String dir, double x, double y){ 
        super(dir);
        this.setPosicion(x, y);
        this.desactivado = false;
        this.tiempo = 10;
    }

    public void temporizador(){
        if(tiempo>0){
            tiempo--;
        }
        else{
            desactivado = true;
        }
    }

    public boolean desactivo(){
        return desactivado;
    }
}