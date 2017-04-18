package model;

/**
 * Die Klasse Route stellt eine Strecke mit einem Anfangs- und Endpunkt dar.
 * @author Nina Grebing
 *
 */

public class Route {
	private Vektor anfang;
	private Vektor ende;

	/**
	 * Konstruktor der Klasse Route
	 * @param anfang Anfangspunkt der Route
	 * @param ende Endpunkt der Route
	 */
	public Route(Vektor anfang, Vektor ende){
		this.setAnfang(anfang);
		this.setEnde(ende);
	}
	 
	/**
	 * Gibt den Ortsvektor der Route an der Stelle x zurück.
	 * @param x x-Koordinate der Route
	 * @return Ortsvektor der Route an der Stelle x
	 */
	public Vektor routeAnStelleX(float x) {
		// Falls Route in dem Bereich nicht definiert ist, wird null
		// zurückgegeben
		if (!((x >= anfang.getX() && x <= ende.getX()) || (x >= ende.getX() && x <= anfang.getX()))) {
			return null;
		}
		/**
		 * Fall die Route wagerecht ist, sind alle y-Werte gleich.
		 */
		if (istWagerecht()) {
			return new Vektor(x, anfang.getY());
		}
		// Geradengleichung: y=mx+n
		// Steigung m
		float m = (ende.getY() - anfang.getY()) / Math.abs(ende.getX() - anfang.getX());
		// y-Achsenabshcnitt n
		float n = anfang.getY() - m * anfang.getX();
		return new Vektor(x, m * x + n);
	}

	/**
	 * Gibt den Ortsvektor der Route an der Stelle y zurück.
	 * @param y y-Koordinate der Route
	 * @return Ortsvektor der Route an der Stelle y
	 */
	public Vektor routeAnStelleY(float y) {
		// Falls Route in dem Bereich nicht definiert ist, wird null
		// zurückgegeben
		if (!((y >= anfang.getY() && y <= ende.getY()) || (y >= ende.getY() && y <= anfang.getY()))) {
			return null;
		}
		//Falls die Route senkrecht ist, sind alle x-Werte gleich
		if (istSenkrecht()){
			return new Vektor(anfang.getX(),y);
		}
		// Geradengleichung: y=mx+n
		// Steigung m
		float m = (ende.getY() - anfang.getY()) / Math.abs(ende.getX() - anfang.getX());
		// y-Achsenabshcnitt n
		float n = anfang.getY() - m * anfang.getX();
		return new Vektor((y - n) / m, y);
	}

	/**
	 * Prüft, ob die Route wagerecht ist.
	 * @return 
	 */
	public boolean istWagerecht() {
		if (anfang.getY() == ende.getY()) {
			return true;
		}
		return false;
	}

	/**
	 * Prüft, ob die Route senkrecht ist.
	 * @return
	 */
	public boolean istSenkrecht() {
		if (anfang.getX() == ende.getX()) {
			return true;
		}
		return false;
	}

	/*
	 * Gette- und Setter-Methoden
	 */
	
	/**
	 * Gibt den Anfangspunkt der Route zurück.
	 * @return Anfangspunkt der Route
	 */
	public Vektor getAnfang() {
		return anfang;
	}

	/**
	 * Setzt den Anfangspunkt der Route.
	 * @param anfang neuer Anfangspunkt der Route
	 */
	public void setAnfang(Vektor anfang) {
		this.anfang = anfang;
	}

	/**
	 * Gibt den Endpunkt der Route zurück.
	 * @return Endepunkt der Route
	 */
	public Vektor getEnde() {
		return ende;
	}

	/**
	 * Setzt den Endpunkt der Route.
	 * @param ende neuer Endpunkt der Route
	 */
	public void setEnde(Vektor ende) {
		this.ende = ende;
	}
	
	/**
	 * Gibt den Anfangs- und Endpunkt der Route zurück. Dies dient der Ausgabe als String.
	 * @return Anfangs- und Endpunkt der Route als String
	 */
	public String toString(){
		return "Punkt A: "+this.getAnfang()+"\n"+"Punkt E: "+this.getEnde()+"\n\n";
	}
}
