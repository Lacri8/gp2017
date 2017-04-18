package model;

/**
 * Die Klasse Str�mung dient der Speicheurng von Str�mungen,
 * welche ein Str�mungsgebiet und einen Str�mungsvektor besitzt.
 * @author Nina Grebing
 *
 */

public class Str�mung {

	//Der Str�mungsvektor der Str�mung, welcher die Richtung und die Geschwindigkeit der Str�mung beinhaltet.
	private Vektor str�mungsVektor;
	//Das Gebiet, in dem die Str�mung gilt.
	private Gebiet str�mungsGebiet;
	
	/**
	 * Konstruktor der Klasse Str�mung
	 * @param gebiet Gebiet der Str�mung
	 * @param str�mung Str�mungsvektor der Str�mung
	 */
	public Str�mung(Gebiet gebiet, Vektor str�mung){
		this.setStr�mungsGebiet(gebiet);
		this.setStr�mungsVektor(str�mung);
	}
	
	/**
	 * Konstruktor der Klasse Str�mung
	 * @param anfang Anfangspunkt des Gebietes der Str�mung
	 * @param ende Endpunkt des Gebietes der Str�mung
	 * @param str�mung Str�mungsvektor der Str�mung
	 */
	public Str�mung(Vektor anfang, Vektor ende, Vektor str�mung){
		this.setStr�mungsGebiet(new Gebiet(anfang,ende));
		this.setStr�mungsVektor(str�mung);
	}


	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den Str�mungsvektor der Str�mung zur�ck.
	 * @return Str�mungsvektor der Strmung
	 */
	public Vektor getStr�mungsVektor() {
		return str�mungsVektor;
	}

	/**
	 * Setzt den Str�mungsvektor der Str�mung.
	 * @param str�mungsVektor neuer Str�mungsvektor der Str�mung
	 */
	public void setStr�mungsVektor(Vektor str�mungsVektor) {
		this.str�mungsVektor = str�mungsVektor;
	}

	/**
	 * Gibt das Str�mungsgebiet der Str�mung zur�ck.
	 * @return Str�mungsgebiet der Str�mung
	 */
	public Gebiet getStr�mungsGebiet() {
		return str�mungsGebiet;
	}

	/**
	 * Setzt das Str�mungsgebiet der Str�mung.
	 * @param str�mungsGebiet neues Str�mungsgebiet der Str�mung.
	 */
	public void setStr�mungsGebiet(Gebiet str�mungsGebiet) {
		this.str�mungsGebiet = str�mungsGebiet;
	}
	
	/**
	 * Gibt die Koordinaten des unteren linken Eckpunktes und des oberen rechten Eckpunktes des Str�mungsgebietes,
	 * sowie die Koordinaten den Str�mungsvektors zur�ck. Dies dient der Ausgabe als String.
	 * @return Daten der Str�mung als String
	 */
	public String toString(){
		return this.getStr�mungsGebiet().getEckpunktA()+" "+this.getStr�mungsGebiet().getEckpunktC()+" SV = "+this.getStr�mungsVektor()+"\n";
	}
}
