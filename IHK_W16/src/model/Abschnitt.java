package model;

/**
 * Die Klasse Abschnitt stellt jeweils eine Teilstrecke der Route dar, welcher in einem Str�mungsgebiet liegt.
 * @author Nina Grebing
 *
 */

public class Abschnitt {
	//die geltende Str�mung des Abschnittes
	private Vektor str�mungsVektor;
	//der Anfangspunkt des Abschnittes
	private Vektor anfang;
	//der Endpunkt des Abschnittes
	private Vektor ende;
	//der zu steuernde Kursvektor auf dem Abschnitt
	private Vektor gesteuerterVektor;
	//die Geschwindigkeit auf dem Abschnitt
	private float geschwindigkeit;
	//die Entfernung des Anfangspunktes zum Endpunkt bzw. die L�nge des Abschnittes
	private float entfernung;
	//die Fahrzeit auf dem Abschnitt
	private float fahrzeit;
	
	
	/**
	 * Konstruktor der Klasse Abschnitt
	 * @param anfang der Anfangspunkt des Abschnittes
	 * @param ende der Endpunkt des Abschnittes
	 * @param str�mung die geltende Str�mung des Abschnittes
	 * @param entfernung die Entfernung des Anfangspunktes zum Endpunkt bzw. die L�nge des Abschnittes
	 * @param skv der zu steuernde Kursvektor auf dem Abschnitt
	 * @param fahrzeit die Fahrzeit auf dem Abschnitt
	 * @param geschwindigkeit die Geschwindigkeit auf dem Abschnitt
	 */
	public Abschnitt(Vektor anfang, Vektor ende, Vektor str�mung, float entfernung, Vektor skv, float fahrzeit,float geschwindigkeit){
		this.setAnfang(anfang);
		this.setEnde(ende);
		this.setStr�mungsVektor(str�mung);
		this.setGeschwindigkeit(geschwindigkeit);
		this.setEntfernung(entfernung);
		this.setFahrzeit(fahrzeit);
		this.setGesteuerterVektor(skv);
	}
		
	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt den Str�munsvektor des Abschnittes zur�ck.
	 * @return Str�munsvektor des Abschnittes
	 */
	public Vektor getStr�mungsVektor() {
		return str�mungsVektor;
	}
	
	/**
	 * Setzt den Str�mungsvektor des Abschnittes.
	 * @param str�mungsVektor neuer Str�mungsvektor des Abschnittes
	 */
	public void setStr�mungsVektor(Vektor str�mungsVektor) {
		this.str�mungsVektor = str�mungsVektor;
	}
	
	/**
	 * Gibt den Anfangspunkt des Abschnittes zur�ck.
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
	 * Gibt den Endpunkt des Abschnittes zur�ck.
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
	 * Gibt den zu steuernden Kursvektor auf dem Abschnitt zur�ck.
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
	 * Gibt die Sollgeschwindigkeit auf dem Abschnitt zur�ck.
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
	 * Gibt die L�nge des Abschnittes zur�ck.
	 * @return die L�nge des Abschnittes
	 */
	public float getEntfernung() {
		return entfernung;
	}
	
	/**
	 * Setzt die L�nge des Abschnittes.
	 * @param entfernung neue L�nge des Abschnittes
	 */
	public void setEntfernung(float entfernung) {
		this.entfernung = entfernung;
	}
	
	/**
	 * Gibt die Fahrzeit auf dem Abschnitt zur�ck.
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
	 * Gibt die Daten des Abshcnittes zur�ck, in der Form wie sie f�r die Ausgabe als String erwartet wird.
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
