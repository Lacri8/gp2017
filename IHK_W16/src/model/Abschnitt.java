package model;

/**
 * Die Klasse Abschnitt stellt jeweils eine Teilstrecke der Route dar, welcher in einem Strömungsgebiet liegt.
 * @author Nina Grebing
 *
 */

public class Abschnitt {
	//die geltende Strömung des Abschnittes
	private Vektor strömungsVektor;
	//der Anfangspunkt des Abschnittes
	private Vektor anfang;
	//der Endpunkt des Abschnittes
	private Vektor ende;
	//der zu steuernde Kursvektor auf dem Abschnitt
	private Vektor gesteuerterVektor;
	//die Geschwindigkeit auf dem Abschnitt
	private float geschwindigkeit;
	//die Entfernung des Anfangspunktes zum Endpunkt bzw. die Länge des Abschnittes
	private float entfernung;
	//die Fahrzeit auf dem Abschnitt
	private float fahrzeit;
	
	
	/**
	 * Konstruktor der Klasse Abschnitt
	 * @param anfang der Anfangspunkt des Abschnittes
	 * @param ende der Endpunkt des Abschnittes
	 * @param strömung die geltende Strömung des Abschnittes
	 * @param entfernung die Entfernung des Anfangspunktes zum Endpunkt bzw. die Länge des Abschnittes
	 * @param skv der zu steuernde Kursvektor auf dem Abschnitt
	 * @param fahrzeit die Fahrzeit auf dem Abschnitt
	 * @param geschwindigkeit die Geschwindigkeit auf dem Abschnitt
	 */
	public Abschnitt(Vektor anfang, Vektor ende, Vektor strömung, float entfernung, Vektor skv, float fahrzeit,float geschwindigkeit){
		this.setAnfang(anfang);
		this.setEnde(ende);
		this.setStrömungsVektor(strömung);
		this.setGeschwindigkeit(geschwindigkeit);
		this.setEntfernung(entfernung);
		this.setFahrzeit(fahrzeit);
		this.setGesteuerterVektor(skv);
	}
		
	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den Strömunsvektor des Abschnittes zurück.
	 * @return Strömunsvektor des Abschnittes
	 */
	public Vektor getStrömungsVektor() {
		return strömungsVektor;
	}
	
	/**
	 * Setzt den Strömungsvektor des Abschnittes.
	 * @param strömungsVektor neuer Strömungsvektor des Abschnittes
	 */
	public void setStrömungsVektor(Vektor strömungsVektor) {
		this.strömungsVektor = strömungsVektor;
	}
	
	/**
	 * Gibt den Anfangspunkt des Abschnittes zurück.
	 * @return Anfangspunkt des Abschnittes
	 */
	public Vektor getAnfang() {
		return anfang;
	}
	
	/**.
	 * Setzt den Anfangspunkt des Abschnittes.
	 * @param anfang neuer Anfangspunkt des Abschnittes
	 */
	public void setAnfang(Vektor anfang) {
		this.anfang = anfang;
	}
	
	/**
	 * Gibt den Endpunkt des Abschnittes zurück.
	 * @return Endpunkt des Abschnittes
	 */
	public Vektor getEnde() {
		return ende;
	}
	
	/**
	 * Setzt den Endpunkt des Abschnittes.
	 * @param ende neuer Endpunkt des Abschnittes
	 */
	public void setEnde(Vektor ende) {
		this.ende = ende;
	}
	
	/**
	 * Gibt den zu steuernden Kursvektor auf dem Abschnitt zurück.
	 * @return zu steuernden Kursvektor auf dem Abschnitt
	 */
	public Vektor getGesteuerterVektor() {
		return gesteuerterVektor;
	}
	
	/**
	 * Setzt den zu steuernden Kursvektor auf dem Abschnitt.
	 * @param gesteuerterVektor neuer zu steuernden Kursvektor auf dem Abschnitt.
	 */
	public void setGesteuerterVektor(Vektor gesteuerterVektor) {
		this.gesteuerterVektor = gesteuerterVektor;
	}
	
	/**
	 * Gibt die Sollgeschwindigkeit auf dem Abschnitt zurück.
	 * @return die Sollgeschwindigkeit auf dem Abschnitt
	 */
	public float getGeschwindigkeit() {
		return geschwindigkeit;
	}
	
	/**
	 * Setzt die Sollgeschwindigkeit auf dem Abschnitt.
	 * @param geschwindigkeit neue Sollgeschwindigkeit auf dem Abschnitt
	 */
	public void setGeschwindigkeit(float geschwindigkeit) {
		this.geschwindigkeit = geschwindigkeit;
	}
	
	/**
	 * Gibt die Länge des Abschnittes zurück.
	 * @return die Länge des Abschnittes
	 */
	public float getEntfernung() {
		return entfernung;
	}
	
	/**
	 * Setzt die Länge des Abschnittes.
	 * @param entfernung neue Länge des Abschnittes
	 */
	public void setEntfernung(float entfernung) {
		this.entfernung = entfernung;
	}
	
	/**
	 * Gibt die Fahrzeit auf dem Abschnitt zurück.
	 * @return die Fahrzeit auf dem Abschnitt
	 */
	public float getFahrzeit() {
		return fahrzeit;
	}
	
	/**
	 * Setzt die Fahrzeit auf dem Abschnitt.
	 * @param fahrzeit neue Fahrzeit auf dem Abschnitt
	 */
	public void setFahrzeit(float fahrzeit) {
		this.fahrzeit = fahrzeit;
	}
	
	/**
	 * Gibt die Daten des Abshcnittes zurück, in der Form wie sie für die Ausgabe als String erwartet wird.
	 * @return Daten des Abschnittes als String
	 */
	public String toString(){
		String daten;
		//Anfangs- und Endpunkt des Abshcnittes
		daten="Strecke von "+this.getAnfang()+" nach "+this.getEnde()+"\n";
		//Entfernung des Anfanungs- und Endpunktes
		daten+="Entfernung: "+this.getEntfernung()+" km\n";
		//Der zu steuernde Kursvektor auf dem Abschnitt
		daten+="SKV = "+this.getGesteuerterVektor()+"\n";
		//Die Geschwindigkeit auf dem Abschnitt
		daten+="Sollgeschwindigkeit: "+this.getGeschwindigkeit()+" km/h\n";
		//Die Fahrzeit auf dem Abschnitt
		daten+="Fahrzeit: "+this.getFahrzeit()+" h\n\n";
		return daten;
	}
}
