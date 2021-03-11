 public class Camara {
	private double x,y;
	private double resX, resY;

    public Camara(double x,double y) {
    	this.x = x;
    	this.y = y;
    }

	public void seguirPersonaje(Heroe obj){
		//this.x = -b.getX()+(m.getWidth()/8);
		this.x = -obj.getX()+resX/2;
		if (this.x>0){
				this.x = 0;
		}

		if(this.x < -(Escenario.getAncho()-resX)){
			this.x = -(Escenario.getAncho()-resX);
		}

	}

	public void setViewPort(double x,double y){
		setRegionVisible(x,y);
	}

	public void setRegionVisible(double x,double y){
		resX = x;
		resY = y;
	}

    public void setX(double x){
    	this.x = x;
	}
	
     public void setY(double y){
    	this.y = y;
	}
	
    public double getX(){
    	return this.x;
	}
	
     public double getY(){
    	return this.y;
    }
}