package model;

/**
 * Die Klasse Strömung dient der Speicheurng von Strömungen,
 * welche ein Strömungsgebiet und einen Strömungsvektor besitzt.
 * @author Nina Grebing
 *
 */

public class Strömung {

	//Der Strömungsvektor der Strömung, welcher die Richtung und die Geschwindigkeit der Strömung beinhaltet.
	private Vektor strömungsVektor;
	//Das Gebiet, in dem die Strömung gilt.
	private Gebiet strömungsGebiet;
	
	/**
	 * Konstruktor der Klasse Strömung
	 * @param gebiet Gebiet der Strömung
	 * @param strömung Strömungsvektor der Strömung
	 */
	public Strömung(Gebiet gebiet, Vektor strömung){
		this.setStrömungsGebiet(gebiet);
		this.setStrömungsVektor(strömung);
	}
	
	/**
	 * Konstruktor der Klasse Strömung
	 * @param anfang Anfangspunkt des Gebietes der Strömung
	 * @param ende Endpunkt des Gebietes der Strömung
	 * @param strömung Strömungsvektor der Strömung
	 */
	public Strömung(Vektor anfang, Vektor ende, Vektor strömung){
		this.setStrömungsGebiet(new Gebiet(anfang,ende));
		this.setStrömungsVektor(strömung);
	}


	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den Strömungsvektor der Strömung zurück.
	 * @return Strömungsvektor der Strmung
	 */
	public Vektor getStrömungsVektor() {
		return strömungsVektor;
	}

	/**
	 * Setzt den Strömungsvektor der Strömung.
	 * @param strömungsVektor neuer Strömungsvektor der Strömung
	 */
	public void setStrömungsVektor(Vektor strömungsVektor) {
		this.strömungsVektor = strömungsVektor;
	}

	/**
	 * Gibt das Strömungsgebiet der Strömung zurück.
	 * @return Strömungsgebiet der Strömung
	 */
	public Gebiet getStrömungsGebiet() {
		return strömungsGebiet;
	}

	/**
	 * Setzt das Strömungsgebiet der Strömung.
	 * @param strömungsGebiet neues Strömungsgebiet der Strömung.
	 */
	public void setStrömungsGebiet(Gebiet strömungsGebiet) {
		this.strömungsGebiet = strömungsGebiet;
	}
	
	/**
	 * Gibt die Koordinaten des unteren linken Eckpunktes und des oberen rechten Eckpunktes des Strömungsgebietes,
	 * sowie die Koordinaten den Strömungsvektors zurück. Dies dient der Ausgabe als String.
	 * @return Daten der Strömung als String
	 */
	public String toString(){
		return this.getStrömungsGebiet().getEckpunktA()+" "+this.getStrömungsGebiet().getEckpunktC()+" SV = "+this.getStrömungsVektor()+"\n";
	}
}
