package control;

import java.io.IOException;
import java.util.ArrayList;

import model.Abschnitt;
import model.Gebiet;
import model.Route;
import model.Strömung;
import model.Vektor;

/**
 * Die Klasse RoutenPlaner berechnet zu einer gegeben Route, einem gegebenem Gebiet und gegebenen Strömungen, die Abschnitte auf der Route.
 * Die Abschnitte beschreiben dabei, in welche Richtung, mit welcher Geschwindigkeit und wie lange ein Schiff fahren muss,
 * um unter Einfluss der gegenwärtigen Strömung mit 5km/h entlang der vorgegeben Route zu fahren.
 * @author Nina Grebing
 */

public class RoutenPlaner {
	//Gesamtgebiet
	private Gebiet dasGebiet;
	//Route
	private Route dieRoute;
	//Beinhaltet alle Strömungen
	private ArrayList <Strömung> strömungen;
	//Beinhaltet alle Schnittpunkte
	private ArrayList <Vektor> schnittpunkte;
	//Ein Hilfsfeld zur Suche der Schnittpunkte
	private Vektor[] schnittpunkte_tmp=new Vektor[2];
	//Beinhaltet alle Abschnitte
	private ArrayList <Abschnitt> abschnitte;
	//Eingabe der Daten
	private Eingabe eingabe;
	//Beschreibung des Falls
	private String beschreibung;
	//Datei-Name der input-Datei
	private String input;
	
	/**
	 * Konstruktor der Klasse RoutenPlaner
	 * @param input Datei-Name der input-Datei
	 * @throws IOException
	 */
	public RoutenPlaner(String input){		
		try {
			this.setEingabe(new Eingabe(input));
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		}
		this.setInput(input);
		this.setSchnittpunkte(new ArrayList <Vektor>());
		this.setAbschnitte(new ArrayList <Abschnitt>());
		this.setStrömungen(new ArrayList <Strömung>());
		//Ausführung der Methoden des Routenplaners
		try {
			this.routine();
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		}
		
	}

	/**
	 * Methode, die die auszuführenden Methoden des Routenplaner in der richtigen Reihenfolge aufruft.
	 * @throws Exception 
	 */
	public void routine() throws Exception{
		leseEingabe();
		holeAlleSchnittpunkte();
		erstelleAbschnitte();
	}

	/**
	 * Weist den Variablen die Werte zu, die die Eingabe aus der Eingabe-Datei gelesen hat.
	 * @throws Exception 
	 */	
	public void leseEingabe() throws Exception{
		Vektor a,c;
		this.setDasGebiet(eingabe.getDasGebiet());
		//prüfen, ob das Gebiet zu groß gewählt wurd
		if(this.getDasGebiet().getEckpunktC().getX()*this.getDasGebiet().getEckpunktC().getY()>=100000000){
			throw new Exception("Das übergebene Gebiet darf nicht größer als 100000000 Quadratkilometer sein!");
		}
		a=this.getDasGebiet().getEckpunktA();
		c=this.getDasGebiet().getEckpunktC();
		//prüfen, ob negative x- oder y-Werte vorhanden sind
		if(a.getX()<0||a.getY()<0||c.getX()<0||c.getY()<0){
			throw new Exception("Alle Punkte müssen im 1.Quadranten liegen!");
		}
		//prüfen, ob das Gebiet breit bzw. lang genug ist
		if(c.getX()-a.getX()<1||c.getY()-a.getY()<1){
			throw new Exception("Ein Gebiet muss mindestens 1km breit und 1km lang sein!");
		}
		this.setDieRoute(eingabe.getDieRoute());
		//prüfen, ob die Punkte der Route außerhalb des Gebietes liegen
		if(!this.getDasGebiet().enthältPunkt(this.getDieRoute().getAnfang())||!this.getDasGebiet().enthältPunkt(this.getDieRoute().getEnde())){
			throw new Exception("Der Anfangs- und Endpunkt der Route müssen innerhalb des Gebietes liegen!");
		}
		//prüfen, ob Anfangspunkt und Endpunkt identisch sind
		if(this.getDieRoute().getAnfang().equals(this.getDieRoute().getEnde())){
			throw new Exception("Der Anfangspunkt und der Endpunkt dürfen nicht identisch sein!");
		}
		this.setStrömungen(eingabe.getStrömungen());
		for(int i=0;i<strömungen.size();i++){
			//prüfen, ob Strömungsgebiete außerhalb des Gebietes liegen
			if(!this.getDasGebiet().enthältGebiet(strömungen.get(i).getStrömungsGebiet())){
				throw new Exception("Alle Strömungsgebiete müssen innerhalb des Gebietes liegen!");
			}
			a=strömungen.get(i).getStrömungsGebiet().getEckpunktA();
			c=strömungen.get(i).getStrömungsGebiet().getEckpunktC();
			//prüfen, ob Gebiete richtig definiert wurden
			if(a.getX()>c.getX()||a.getY()>c.getY()){
				throw new Exception("Es muss zuerst der untere linke Punkt und dann der obere rechte Punkt eines Gebietes übergeben werden!");
			}
			//prüfen, ob das Gebiet breit bzw. lang genug ist
			if(c.getX()-a.getX()<1||c.getY()-a.getY()<1){
				throw new Exception("Ein Gebiet muss mindestens 1km breit und 1km lang sein!");
			}
		}
		this.setBeschreibung(eingabe.getBeschreibung());
	}
	
	/**
	 * Speichert alle Schnittpunkte, die zur Bestimmung der Abschnitte benötigt werden in {@schnittpunkte}.
	 */
	public void holeAlleSchnittpunkte(){
		schnittpunkte.add(dieRoute.getAnfang());
		for(int i=0;i<strömungen.size();i++){
			sucheSchnittpunkte(strömungen.get(i).getStrömungsGebiet());
			//überprüfe ob Schnittpunkt in einer späteren Strömung liegt und deshalb verworfen wird
			for(int k=0;k<2;k++){
				if(schnittpunkte_tmp[k]!=null){
					for(int j=i+1;j<strömungen.size();j++){
						if(strömungen.get(j).getStrömungsGebiet().enthältPunkt(schnittpunkte_tmp[k])){
							//Schnittpunkt entfernen
							schnittpunkte_tmp[k]=null;
							break;
						}
					}
				}
			}
			//Schnittpunkte übernehmen
			for(int k=0;k<2;k++){
				if(schnittpunkte_tmp[k]!=null){
					schnittpunkte.add(schnittpunkte_tmp[k]);
					schnittpunkte_tmp[k]=null;
				}
			}
		}
		schnittpunkte.add(dieRoute.getEnde());
	}
	
	/**
	 * Speichert alle Schnittpunkte der Route mit dem Gebiet @param strömungsgebiet im Feld schnittpunkte_tmp.
	 * Dies können maximal zwei Punkte sein.
	 * @param strömungsgebiet Gebiet, zu dem die Schnittpunkte mit der Route gesucht werden.
	 */
	public void sucheSchnittpunkte(Gebiet strömungsgebiet){
		//Grenzpunkte des Gebietes
		float x1=strömungsgebiet.getEckpunktA().getX();
		float x2=strömungsgebiet.getEckpunktC().getX();
		float y1=strömungsgebiet.getEckpunktA().getY();
		float y2=strömungsgebiet.getEckpunktC().getY();
		//
		int zähler=0;
		float x,y;
		
		//wenn die Route senkrecht ist, werden keine senkrechten Seiten der Strömungsgebiete überprüft
		if(!dieRoute.istSenkrecht()){
			//linke Seite des Gebiets
			if(dieRoute.routeAnStelleX(x1)!=null){
				y=dieRoute.routeAnStelleX(x1).getX();
				if(y>=y1&&y<=y2){
					schnittpunkte_tmp[zähler]=dieRoute.routeAnStelleX(x1);
					zähler+=1;
				}
			}
			
			//rechts Seite des Gebiets
			if(dieRoute.routeAnStelleX(x2)!=null){
				y=dieRoute.routeAnStelleX(x2).getX();
				if(y>=y1&&y<=y2){
					schnittpunkte_tmp[zähler]=dieRoute.routeAnStelleX(x2);
					zähler+=1;
				}
			}
		}
		//Abbruchbedingung (es können maximal 2 Seiten eines Gebietes geschnitten werden)
		if(zähler==2){
			return;
		}
		//wenn die Route wagerecht ist, werden keine wagerechten Seiten der Strömungsgebiete überprüft
		if(!dieRoute.istWagerecht()){
			//untere Seite des Gebiets
			if(dieRoute.routeAnStelleY(y1)!=null){
				x=dieRoute.routeAnStelleY(y1).getX();
				if(x>=x1&&x<=x2){
					//Falls der Punkt ein Eckpunkt ist, könnte er bereits hinzugefügt worden sein
					if(!tmpSchnittpunkteEnthält(dieRoute.routeAnStelleY(y1))){
						schnittpunkte_tmp[zähler]=dieRoute.routeAnStelleY(y1);
						zähler+=1;
					}
				}
			}
			//Abbruchbedingung (es können maximal 2 Seiten eines Gebietes geschnitten werden)
			if(zähler==2){
				return;
			}
			//obere Seite des Gebiets
			if(dieRoute.routeAnStelleY(y2)!=null){
				x=dieRoute.routeAnStelleY(y2).getX();
				if(x>=x1&&x<=x2){
					//Falls der Punkt ein Eckpunkt ist, könnte er bereits hinzugefügt worden sein
					if(!tmpSchnittpunkteEnthält(dieRoute.routeAnStelleY(y2))){
						schnittpunkte_tmp[zähler]=dieRoute.routeAnStelleY(y2);
						zähler+=1;
					}
				}
			}
			
		}
		
	}
	
	/**
	 * Überprüft, ob das Feld @param schnittpunkte_tmp einen Vektor enthält.
	 * @param v zu überprüfender Vektor
	 * @return
	 */
	public boolean tmpSchnittpunkteEnthält(Vektor v){
		if(schnittpunkte_tmp[0]==null || schnittpunkte_tmp[0]!=v){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Sortiert die Schnittpunkte, wobei der erste Punkt der Anfangspunkt der Route ist und
	 * alle folgenden Punkte nach der geringsten Entfernung zum Anfangspunkt sortiert werden.
	 */
	public void sortiereSchnittpunkte(){
		entferneDoppelteSchnittpunkte();
		ArrayList <Vektor> tmp=new ArrayList<Vektor>();
		Vektor abstandsVektor,next=null;
		abstandsVektor=new Vektor(schnittpunkte.get(0).getX()-this.getDieRoute().getAnfang().getX(),schnittpunkte.get(0).getY()-this.getDieRoute().getAnfang().getY());
		float abstand,abstand_next;
		abstand_next=abstandsVektor.getNorm();
		boolean neuerDurchgang=true;
		//die ArrayList schnittpunkte wird solange durchlaufen, bis sie leer ist
		while(!schnittpunkte.isEmpty()){
			//durchläuft schnittpunkte komplett
			for(int i=0;i<schnittpunkte.size();i++){
				if(schnittpunkte.get(i)==null){
					continue;
				}else{
					//berechnet den Abstand des aktuellen Schnittpunktes zum Anfangsvektor
					abstandsVektor.setX(schnittpunkte.get(i).getX()-this.getDieRoute().getAnfang().getX());
					abstandsVektor.setY(schnittpunkte.get(i).getY()-this.getDieRoute().getAnfang().getY());
					abstand=abstandsVektor.getNorm();
					if(neuerDurchgang){
						abstand_next=abstand;
						neuerDurchgang=false;
					}
					//falls next noch null ist, wird automatisch der aktuelle Vektor zu next
					if(next==null){
						next=schnittpunkte.get(i);
						abstand_next=abstand;
					//falls der aktuelle Vektor einen kleineren Abstand zum Anfangsvektor hat, wird er zum neuen next 
					}else if(abstand<abstand_next){
						next=schnittpunkte.get(i);
						abstand_next=abstand;
					}
				}
			}
			//es wird immer der Vektor mit dem kleinsten Abstand zum Anfangspunkt in tmp gespeichert und auf schnittpunkte entfernt
			tmp.add(next);
			schnittpunkte.remove(next);
			next=null;
			neuerDurchgang=true;
		}
		
		schnittpunkte.clear();
		//schnittpunkte erthält alle vorherigen Punkte in sortierter Reihenfolge aus tmp
		schnittpunkte=tmp;
	}
	
	/**
	 * Entfernt alle Schnittpunkte, die doppelt vorhanden sind.
	 */
	public void entferneDoppelteSchnittpunkte(){
		for(int i=0;i<schnittpunkte.size();i++){
			for(int j=i+1;j<schnittpunkte.size();j++){
				if(schnittpunkte.get(i).equals(schnittpunkte.get(j))){
					schnittpunkte.remove(j);
				}
			}
			
		}
	}
	
	/**
	 * Erstellt alle Abschnitte der Route.
	 */
	public void erstelleAbschnitte(){
		sortiereSchnittpunkte();
		Abschnitt neuerAbschnitt;
		Vektor anfang, ende, mittelpunkt, strömung, entfernungsVektor, skv,rkv;
		float fahrzeit, entfernung,geschwindigkeit;
		strömung=new Vektor(0,0);
		//ein Abschnitt besteht jeweils aus 2 Schnittpunkten
		for(int i=0;i<schnittpunkte.size()-1;i++){
			anfang=schnittpunkte.get(i);
			ende=schnittpunkte.get(i+1);
			//der Mittelpunkt des Abschnitts dient der Bestimmung des Strömungsgebietes
			mittelpunkt=new Vektor(anfang.getX()+(ende.getX()-anfang.getX())/2,anfang.getY()+(ende.getY()-anfang.getY())/2);
			strömung.set(0,0);
			//Bestimmung der Strömung für den Abschnitt
			for(int j=strömungen.size()-1;j>=0;j--){
				if(strömungen.get(j).getStrömungsGebiet().enthältPunkt(mittelpunkt)){
					//Strömungsvektor
					strömung.set(strömungen.get(j).getStrömungsVektor().getX(),(strömungen.get(j).getStrömungsVektor().getY()));
					break;
				}
			}
			//Berechnung der Daten des Abschnittes
			entfernungsVektor = new Vektor(ende.getX()-anfang.getX(),ende.getY()-anfang.getY());
			entfernung=entfernungsVektor.getNorm();
			//resultierender Kursvektor
			rkv=new Vektor(5/entfernungsVektor.getNorm()*entfernungsVektor.getX(),5/entfernungsVektor.getNorm()*entfernungsVektor.getY());
			//gesteuerter Kursvektor
			skv=new Vektor(rkv.getX()-strömung.getX(),rkv.getY()-strömung.getY());
			geschwindigkeit=skv.getNorm();
			fahrzeit=entfernung/geschwindigkeit;
						
			//erzeugt einen neuen Abschnitt und speichert diesen in der ArrayList abschnitte 
			neuerAbschnitt=new Abschnitt(anfang,ende,strömung,entfernung,skv,fahrzeit,geschwindigkeit);
			abschnitte.add(neuerAbschnitt);	
		}

	}
	
	/**
	 * Kürzt die Werte aller Abschnitte. Dies dient der leserlicheren Ausgabe.
	 */
	public void kürzeAbschnittsWerte(){
		Abschnitt a;
		for(int i=0;i<abschnitte.size();i++){
			a=abschnitte.get(i);
			a.setAnfang(new Vektor(kürze(a.getAnfang().getX(),1),kürze(a.getAnfang().getY(),1)));
			a.setEnde(new Vektor(kürze(a.getEnde().getX(),1),kürze(a.getEnde().getY(),1)));
			a.setStrömungsVektor(new Vektor(kürze(a.getStrömungsVektor().getX(),1),kürze(a.getStrömungsVektor().getY(),1)));
			a.setGesteuerterVektor(new Vektor(kürze(a.getGesteuerterVektor().getX(),1),kürze(a.getGesteuerterVektor().getY(),1)));
			a.setEntfernung(kürze(a.getEntfernung(),1));
			a.setFahrzeit(kürze(a.getFahrzeit(),1));
			a.setGeschwindigkeit(kürze(a.getGeschwindigkeit(),1));
		}
	}
	
	/**
	 * Methode zum Kürzen von Float-Werten
	 * @param zahl zu kürzender Wert
	 * @param stellen Anzahl der Nachkommastellen
	 * @return
	 */
	public static float kürze(float zahl, int stellen){
		float genauigkeit=1.0F;
		
		genauigkeit*=Math.pow(10, stellen);
		return((int) (zahl*genauigkeit+0.5)/genauigkeit);
	}
	
	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt das Gebiet zurück.
	 * @return das Gebiet
	 */
	public Gebiet getDasGebiet() {
		return dasGebiet;
	}

	/**
	 * Setzt das Gebiet.
	 * @param dasGebiet neues Gebiet
	 */
	public void setDasGebiet(Gebiet dasGebiet) {
		this.dasGebiet = dasGebiet;
	}

	/**
	 * Gibt die Route zurück.
	 * @return Route
	 */
	public Route getDieRoute() {
		return dieRoute;
	}

	/**
	 * Setzt die Route.
	 * @param dieRoute neue Route
	 */
	public void setDieRoute(Route dieRoute) {
		this.dieRoute = dieRoute;
	}

	/**
	 * Gibt die Abschnitte für die Route zurück.
	 * @return die Abschnitte für die Route
	 */
	public ArrayList <Abschnitt> getAbschnitte() {
		return abschnitte;
	}

	/**
	 * Setzt die Abschnitte für die Route.
	 * @param abschnitte neue Abschnitte für die Route
	 */
	public void setAbschnitte(ArrayList <Abschnitt> abschnitte) {
		this.abschnitte = abschnitte;
	}

	/**
	 * Setzt die Strömungen.
	 * @param strömungen neue Strömungen
	 */
	public void setStrömungen(ArrayList <Strömung> strömungen){
		this.strömungen=strömungen;		
	}
	
	/**
	 * Gibt die Strömungen zurück.
	 * @return die Strömungen
	 */
	public ArrayList <Strömung> getStrömungen(){
		return this.strömungen;
	}
	
	/**
	 * Setzt die Schnittpunkte der Route.
	 * @param schnittpunkte neue Schnittpunkte der Route
	 */
	public void setSchnittpunkte(ArrayList <Vektor> schnittpunkte){
		this.schnittpunkte=schnittpunkte;		
	}
	
	/**
	 * Gibt die Schnittpunkte der Route zurück.
	 * @return Schnittpunkte der Route
	 */
	public ArrayList <Vektor> getSchnittpunkte(){
		return this.schnittpunkte;
	}
	
	/**
	 * Gibt die Beschreibung des Falls zurück.
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
	 * Gibt den Namen der Eingabe-Datei zurück.
	 * @return Name der Eingabe-Datei
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Setzt den Namen der Eingabe-Datei.
	 * @param input neuer Name der Eingabe-Datei
	 */
	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * Gibt die Eingabe zurück.
	 * @return die Eingabe
	 */
	public Eingabe getEingabe() {
		return eingabe;
	}

	/**
	 * Setzt die Eingabe.
	 * @param eingabe neue Eingabe
	 */
	public void setEingabe(Eingabe eingabe) {
		this.eingabe = eingabe;
	}
	
}
