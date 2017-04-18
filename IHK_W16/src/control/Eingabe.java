package control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import model.Gebiet;
import model.Route;
import model.Str�mung;
import model.Vektor;

/**
 * Die Klasse Eingabe liest die Daten aus der ihr �bergebenen Datei ein.
 * @author Nina Grebing
 */

public class Eingabe {
	private BufferedReader br;
	private String beschreibung;
	private Route dieRoute;
	private Gebiet dasGebiet;
	private ArrayList<Str�mung> str�mungen;

	/**
	 * Konstruktor der Klasse Eigabe
	 * @param input Pfad zur Eingabe-Datei
	 * @throws Exception
	 */
	public Eingabe(String input) throws Exception{
		if(!input.endsWith(".in")){
			throw new Exception("Der Name der Eingabe-Datei muss mit '.in' enden!");
		}
		this.setBr(new BufferedReader(new FileReader(new File(input))));
		this.setBeschreibung("");
		this.setStr�mungen(new ArrayList<Str�mung>());
		this.einlesen();
	}
	
	/**
	 * Liest alle Daten aus der Eingabe-Datei ein und speichert sie.
	 * @throws Exception
	 */
	public void einlesen() throws Exception{
		String line;
		String gebiet[]=new String[2];
		String route[]=new String[4];
		String str�mung[]=new String[6];
		
		//Beschreibung einlesen
		for(int i=0;i<3;i++){
			line=br.readLine();
			if(!line.startsWith(";")){
				throw new Exception("Die ersten drei Zeilen der Eingabe-Datei m�ssen Kommentarzeilen sein!");
			}
			line=line.substring(1);
			this.setBeschreibung(this.getBeschreibung()+"\n"+line);
		}
				
		//Gebiet einlesen
		while((line=br.readLine())!=null){
			if(line.startsWith("; Groesse des Gebietes")){
				line=br.readLine();
				gebiet=line.split(" ");
				break;
			}
		}
		this.setDasGebiet(new Gebiet(new Vektor(0,0),new Vektor(Float.parseFloat(gebiet[0]),Float.parseFloat(gebiet[1]))));
		
		//Route einlesen
		while((line=br.readLine())!=null){
			if(line.startsWith("; zu fahrende Route")){
				line=br.readLine();
				route=line.split(" ");
				break;
			}
		}
		this.setDieRoute(new Route(new Vektor(Float.parseFloat(route[0]),Float.parseFloat(route[1])),new Vektor(Float.parseFloat(route[2]),Float.parseFloat(route[3]))));
		
		//Str�mungen einlesen
		while((line=br.readLine())!=null){
			if(line.startsWith("; Stroemungen")){
				while((line=br.readLine())!=null){
					str�mung=line.split(" ");
					neueStr�mung(str�mung);
				}
			}
		}	
	}
	
	/**
	 * F�gt eine neue Str�mung hinzu.
	 * @param str�mung neue Str�mung als String-Feld
	 */
	private void neueStr�mung(String[] str�mung) {
		Vektor anfang, ende, str�mungsvektor;
		anfang=new Vektor(Float.parseFloat(str�mung[0]),Float.parseFloat(str�mung[1]));
		ende=new Vektor(Float.parseFloat(str�mung[2]),Float.parseFloat(str�mung[3]));
		str�mungsvektor=new Vektor(Float.parseFloat(str�mung[4]),Float.parseFloat(str�mung[5]));
		this.str�mungen.add(new Str�mung(anfang,ende,str�mungsvektor));
	}

	/**
	 * Gibt den BufferedReader der Eingabe zur�ck.
	 * @return BufferedReader der Eingabe
	 */
	public BufferedReader getBr() {
		return br;
	}

	/**
	 * Setzt den BufferedReader der Eingabe.
	 * @param br neuer BufferedReader der Eingabe
	 */
	public void setBr(BufferedReader br) {
		this.br = br;
	}

	/**
	 * Gibt die Beschreibung des Falls zur�ck.
	 * @return Beschreibung des Falls
	 */
	public String getBeschreibung() {
		return beschreibung;
	}

	/**
	 * Setzt die Beschreibung des Falls.
	 * @param beschreibung neue Beschreibung des Falls
	 */
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	/**
	 * Gibt die Route des Falls zur�ck.
	 * @return Route des Falls
	 */
	public Route getDieRoute() {
		return dieRoute;
	}

	/**
	 * Setzt die Route des Falls.
	 * @param dieRoute neue Route des Falls
	 */
	public void setDieRoute(Route dieRoute) {
		this.dieRoute = dieRoute;
	}

	/**
	 * Gibt das Gebiet des Falls zur�ck.
	 * @return Gebiet des Falls
	 */
	public Gebiet getDasGebiet() {
		return dasGebiet;
	}

	/**
	 * Setzt das Gebiet des Falls.
	 * @param dasGebiet neues Gebiet des Falls
	 */
	public void setDasGebiet(Gebiet dasGebiet) {
		this.dasGebiet = dasGebiet;
	}

	/**
	 * Gibt die Str�mungen des Falls zur�ck.
	 * @return Str�mungen des Falls
	 */
	public ArrayList<Str�mung> getStr�mungen() {
		return str�mungen;
	}

	/**
	 * Setzt die Str�mungen des Falls.
	 * @param str�mungen neue Str�mungen des Falls
	 */
	public void setStr�mungen(ArrayList<Str�mung> str�mungen) {
		this.str�mungen = str�mungen;
	}

}
