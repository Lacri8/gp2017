package view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import control.RoutenPlaner;

/**
 * Die Klasse Ausgabe gibt die Eingabe und die vom Routenplaner berechneten Abschnitte in einer Textdatei aus.
 * @author Nina Grebing
 */

public class Ausgabe {

	private RoutenPlaner routenplaner;
	private BufferedWriter bw;
	
	/**
	 * Konstruktor der Klasse Ausgabe
	 * @param routenplaner der Routenplaner, dessen Daten verwendet werden sollen
	 * @throws IOException
	 */
	public Ausgabe(RoutenPlaner routenplaner) throws IOException{
		this.setRoutenplaner(routenplaner);
		//Die output-Datei erh�lt den gleichen Namen wie die input-Datei, aber endet auf ".out", w�hrend die input-Datei auf ".in" endet.
		String output=routenplaner.getInput().substring(0, routenplaner.getInput().length()-2)+"out";
		this.setBw(new BufferedWriter(new FileWriter(new File(output))));
		//k�rzt die Abschnittswerte f�r die Ausgabe
		routenplaner.k�rzeAbschnittsWerte();
		//die Methode zur Ausgabe der Daten wird direkt im Konstruktor aufgerufen, weil die Klasse Ausgabe ausschli�lich daf�r genutzt werden soll.
		gebeAus();
		bw.close();
	}
	
	/**
	 * Konstruktor der Klasse Ausgabe
	 * @param routenplaner der Routenplaner, dessen Daten verwendet werden sollen
	 * @param output Pfad zur Ausgabe-Datei
	 * @throws IOException
	 */
	public Ausgabe(RoutenPlaner routenplaner,String output) throws IOException{
		this.setRoutenplaner(routenplaner);
		this.setBw(new BufferedWriter(new FileWriter(new File(output))));
		//k�rzt die Abschnittswerte f�r die Ausgabe
		routenplaner.k�rzeAbschnittsWerte();
		//die Methode zur Ausgabe der Daten wird direkt im Konstruktor aufgerufen, weil die Klasse Ausgabe ausschli�lich daf�r genutzt werden soll.
		gebeAus();
		bw.close();
	}
	
	/**
	 * Methode, die die Daten aus der Eingabe und die Abschnitte, die der Routenplaner berechnet hat in eine Textdatei schreibt.
	 * @throws IOException
	 */
	public void gebeAus() throws IOException{
		//Beschreibung
		bw.write(routenplaner.getBeschreibung()+"\n\n");
		bw.newLine();
		//Gebiet
		bw.write(" Groesse des Gebietes\n"+routenplaner.getDasGebiet());
		//Route
		bw.write(" zu fahrende Route\n"+routenplaner.getDieRoute());
		//Str�mungen
		bw.write(" Stroemungen\n");
		for( int i=0;i<routenplaner.getStr�mungen().size();i++){
			bw.write(routenplaner.getStr�mungen().get(i).toString());
		}
		bw.newLine();
		//Abschnitte
		bw.write(" Daten f�r die Planung der Route:\n");
		for(int i=0;i<routenplaner.getAbschnitte().size();i++){
			bw.write(routenplaner.getAbschnitte().get(i).toString());
		}
	}

	/**
	 * Gibt den Routenplaner zur�ck.
	 * @return Routenplaner
	 */
	public RoutenPlaner getRoutenplaner() {
		return routenplaner;
	}

	/**
	 * Setzt den Routenplaner.
	 * @param routenplaner neuer Routenplaner
	 */
	public void setRoutenplaner(RoutenPlaner routenplaner) {
		this.routenplaner = routenplaner;
	}

	/**
	 * Gibt den BufferedWriter der ausgabe zur�ck.
	 * @return BufferedWriter der Ausgabe
	 */
	public BufferedWriter getBw() {
		return bw;
	}

	/**
	 * Setzt den BufferedWriter der Ausgabe.
	 * @param bw neuer BufferedWriter der Ausgabe
	 */
	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}
}
