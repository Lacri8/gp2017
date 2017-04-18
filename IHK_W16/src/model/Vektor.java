package model;

/**
 * Die Klasse Vektor dient der Speicherung von x- und y-Koordinaten als Vektor. ö
 * @author Nina Grebing
 *
 */

public class Vektor {

	/*
	 * Für das Programm werden lediglich Vektoren mit 2 Werten benötigt.
	 * Die 2 Werte bzw. Koordinaten des Vektors sind x und y.
	 */
	private float x;
	private float y;
	
	/**
	 * Konstruktor der Klasse Vektor
	 * @param x x-Wert des Vektors
	 * @param y y-Wert des Vektors
	 */
	public Vektor(float x, float y){
		this.setX(x);
		this.setY(y);
	}
	
	/**
	 * Gibt die euklidische Norm des Vektors zurück.
	 * Diese entspricht auch der Länge des Vektors.
	 * @return Norm des Vektors
	 */
	public float getNorm(){
		float f=getX()*getX()+getY()*getY();
		double d=f;
		d=Math.sqrt(d);
		f=Float.parseFloat(Double.toString(d));
		return f;
	}
	
	/**
	 * Überprüft, ob ein Vektore identisch mit diesem Vektor ist.
	 * @param v der andere Vektor
	 * @return 
	 */
	public boolean equals(Vektor v){
		if(this.getX()==v.getX()&&this.getY()==v.getY()){
			return true;
		}
		return false;
	}
	

	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den x-Wert des Vektors zurück.
	 * @return x-Wert des Vektors
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Setzt den x-Wert des Vektors.
	 * @param x neuer x-Wert des Vektors
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Gibt den y-Wert des Vektors zurück.
	 * @return y-Wert des Vektors
	 */
	public float getY() {
		return y;
	}

	/**
	 * Setzt den y-Wert des Vektors.
	 * @param y neuer y-Wert des Vektors
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Setzt den x- und y-Wert des Vektors.
	 * @param x x-Wert des Vektors
	 * @param y y-Wert des Vektors
	 */
	public void set(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	/**
	 * Gibt die Koordinaten des Vektors zurück. Dies dient der Ausgabe als String.
	 * @return x- und y-Koordinaten des Vektors als String
	 */
	public String toString(){
		return "("+this.getX()+";"+this.getY()+")";
	}
	
	
}
