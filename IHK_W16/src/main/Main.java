package main;

import java.io.IOException;
import control.RoutenPlaner;
import view.Ausgabe;

/**
 * Di Klasse Main dient zum Ausführen des Programmes.
 * @author Nina Grebing
 *
 */

public class Main {

	/**
	 * Die main-Methode ruft den Routenplaner auf, dieser führt einige Methoden aus,
	 * um die Abschnitte der Route zu bestimmen. Danach wird die Ausgabe aufgerufen,
	 * welche dem Routenplaner übergeben wird, wodurch die Ausgabe alle benötigten
	 * Daten erhält.
	 * @param args Kommandozeilenparameter
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		String input=args[0];
		RoutenPlaner routenplaner=new RoutenPlaner(input);
		//Angabe des Pfades zur Ausgabe-Datei ist otional
		if(args.length>1){
			String output=args[1];
			Ausgabe a=new Ausgabe(routenplaner,output);
		}else{
			/*
			 * Wenn keine Pfad für die Ausgabe-Datei angegeben wurde,
			 * wird der gleiche Pfad wir für die Eingabe-Datei verwendet.
			 * Die Ausgabe-Datei hat dann den gleichen NAmen, wie die input-Datei, aber endet auf ".out".
			 */
			Ausgabe a=new Ausgabe(routenplaner);
		}
	}

}
