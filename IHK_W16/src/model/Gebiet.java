package model;

	/**
	 * Die Klasse Gebiet dient sowohl der Speicherung des Gesamtgebietes f�r den Routenplaner, als auch f�r die Str�mungsgebiete.
	 * Ein Gebiet wird definiert durch seine Eckpunkte A (unten links) und C (oben rechts).
	 * @author Nina Grebing
	 */

public class Gebiet {

	private Vektor eckpunktA;
	private Vektor eckpunktC;
	
	/**
	 * Konstruktor der Klasse Gebiet
	 * @param a unterer linker Eckpunkt
	 * @param c oberer rechter Eckpunkt
	 * @throws Exception 
	 */
	public Gebiet(Vektor a, Vektor c){
		this.setEckpunktA(a);
		this.setEckpunktC(c);
	}
	
	/**
	 * Pr�ft, ob der Vektor v in diesem Gebiet liegt.
	 * @param v zu �berpr�fender Vektor
	 * @return
	 */
	public boolean enth�ltPunkt(Vektor v){
		float x=v.getX();
		float y=v.getY();
		if(x>=eckpunktA.getX()&&x<=eckpunktC.getX()&&y>=eckpunktA.getY()&&y<=eckpunktC.getY()){
			return true;
		}
		return false;
	}
	
	/**
	 * Pr�ft, ob ein anderes Gebiet vollst�ndig in diesem Gebiet enthalten ist.
	 * @param gebiet zu �berpr�fendes Gebiet
	 * @return
	 */
	public boolean enth�ltGebiet(Gebiet gebiet){
		if(this.getEckpunktA().getX()<=gebiet.getEckpunktA().getX()&&this.getEckpunktA().getY()<=gebiet.getEckpunktA().getY()){
			if(this.getEckpunktC().getX()>=gebiet.getEckpunktC().getX()&&this.getEckpunktC().getY()>=gebiet.getEckpunktC().getY()){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den unteren linken Eckpunkt des Gebietes zur�ck.
	 * @return unterer linker Eckpunkt des Gebietes
	 */
	public Vektor getEckpunktA() {
		return eckpunktA;
	}
	
	/**
	 * Setzt den unteren linken Eckpunkt des Gebietes.
	 * @param eckpunktA neuer unterer linker Eckpunkt des Gebietes
	 */
	public void setEckpunktA(Vektor eckpunktA) {
		this.eckpunktA = eckpunktA;
	}
	
	/**
	 * Gibt den oberen rechten Eckpunkt des Gebietes zur�ck.
	 * @return oberer rechter Eckpunkt des Gebietes
	 */
	public Vektor getEckpunktC() {
		return eckpunktC;
	}
	
	/**
	 * Setzt den oberen rechten Eckpunkt des Gebietes.
	 * @param eckpunktC neuer oberer rechter Eckpunkt des Gebietes
	 */
	public void setEckpunktC(Vektor eckpunktC) {
		this.eckpunktC = eckpunktC;
	}
	
	/**
	 * Gibt den oberen rechten Punkt des Gebietes zur�ck. Dies dient der Ausgabe als String.
	 * Diese Methode ist f�r die Ausgabe des Hauptgebietes vorgesehen.
	 * @return oberer rechter Eckpunkt des Gebietes als String
	 */
	public String toString(){
		return this.getEckpunktC().getX()+" "+this.getEckpunktC().getY()+"\n\n";
	}
}
