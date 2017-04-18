package control;

import java.io.IOException;
import java.util.ArrayList;

import model.Abschnitt;
import model.Gebiet;
import model.Route;
import model.Str�mung;
import model.Vektor;

/**
 * Die Klasse RoutenPlaner berechnet zu einer gegeben Route, einem gegebenem Gebiet und gegebenen Str�mungen, die Abschnitte auf der Route.
 * Die Abschnitte beschreiben dabei, in welche Richtung, mit welcher Geschwindigkeit und wie lange ein Schiff fahren muss,
 * um unter Einfluss der gegenw�rtigen Str�mung mit 5km/h entlang der vorgegeben Route zu fahren.
 * @author Nina Grebing
 */

public class RoutenPlaner {
	//Gesamtgebiet
	private Gebiet dasGebiet;
	//Route
	private Route dieRoute;
	//Beinhaltet alle Str�mungen
	private ArrayList <Str�mung> str�mungen;
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
		this.setStr�mungen(new ArrayList <Str�mung>());
		//Ausf�hrung der Methoden des Routenplaners
		try {
			this.routine();
		} catch (Exception e) {
			System.err.println("Exception: "+e.getMessage());
		}
		
	}

	/**
	 * Methode, die die auszuf�hrenden Methoden des Routenplaner in der richtigen Reihenfolge aufruft.
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
		//pr�fen, ob das Gebiet zu gro� gew�hlt wurd
		if(this.getDasGebiet().getEckpunktC().getX()*this.getDasGebiet().getEckpunktC().getY()>=100000000){
			throw new Exception("Das �bergebene Gebiet darf nicht gr��er als 100000000 Quadratkilometer sein!");
		}
		a=this.getDasGebiet().getEckpunktA();
		c=this.getDasGebiet().getEckpunktC();
		//pr�fen, ob negative x- oder y-Werte vorhanden sind
		if(a.getX()<0||a.getY()<0||c.getX()<0||c.getY()<0){
			throw new Exception("Alle Punkte m�ssen im 1.Quadranten liegen!");
		}
		//pr�fen, ob das Gebiet breit bzw. lang genug ist
		if(c.getX()-a.getX()<1||c.getY()-a.getY()<1){
			throw new Exception("Ein Gebiet muss mindestens 1km breit und 1km lang sein!");
		}
		this.setDieRoute(eingabe.getDieRoute());
		//pr�fen, ob die Punkte der Route au�erhalb des Gebietes liegen
		if(!this.getDasGebiet().enth�ltPunkt(this.getDieRoute().getAnfang())||!this.getDasGebiet().enth�ltPunkt(this.getDieRoute().getEnde())){
			throw new Exception("Der Anfangs- und Endpunkt der Route m�ssen innerhalb des Gebietes liegen!");
		}
		//pr�fen, ob Anfangspunkt und Endpunkt identisch sind
		if(this.getDieRoute().getAnfang().equals(this.getDieRoute().getEnde())){
			throw new Exception("Der Anfangspunkt und der Endpunkt d�rfen nicht identisch sein!");
		}
		this.setStr�mungen(eingabe.getStr�mungen());
		for(int i=0;i<str�mungen.size();i++){
			//pr�fen, ob Str�mungsgebiete au�erhalb des Gebietes liegen
			if(!this.getDasGebiet().enth�ltGebiet(str�mungen.get(i).getStr�mungsGebiet())){
				throw new Exception("Alle Str�mungsgebiete m�ssen innerhalb des Gebietes liegen!");
			}
			a=str�mungen.get(i).getStr�mungsGebiet().getEckpunktA();
			c=str�mungen.get(i).getStr�mungsGebiet().getEckpunktC();
			//pr�fen, ob Gebiete richtig definiert wurden
			if(a.getX()>c.getX()||a.getY()>c.getY()){
				throw new Exception("Es muss zuerst der untere linke Punkt und dann der obere rechte Punkt eines Gebietes �bergeben werden!");
			}
			//pr�fen, ob das Gebiet breit bzw. lang genug ist
			if(c.getX()-a.getX()<1||c.getY()-a.getY()<1){
				throw new Exception("Ein Gebiet muss mindestens 1km breit und 1km lang sein!");
			}
		}
		this.setBeschreibung(eingabe.getBeschreibung());
	}
	
	/**
	 * Speichert alle Schnittpunkte, die zur Bestimmung der Abschnitte ben�tigt werden in {@schnittpunkte}.
	 */
	public void holeAlleSchnittpunkte(){
		schnittpunkte.add(dieRoute.getAnfang());
		for(int i=0;i<str�mungen.size();i++){
			sucheSchnittpunkte(str�mungen.get(i).getStr�mungsGebiet());
			//�berpr�fe ob Schnittpunkt in einer sp�teren Str�mung liegt und deshalb verworfen wird
			for(int k=0;k<2;k++){
				if(schnittpunkte_tmp[k]!=null){
					for(int j=i+1;j<str�mungen.size();j++){
						if(str�mungen.get(j).getStr�mungsGebiet().enth�ltPunkt(schnittpunkte_tmp[k])){
							//Schnittpunkt entfernen
							schnittpunkte_tmp[k]=null;
							break;
						}
					}
				}
			}
			//Schnittpunkte �bernehmen
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
	 * Speichert alle Schnittpunkte der Route mit dem Gebiet @param str�mungsgebiet im Feld schnittpunkte_tmp.
	 * Dies k�nnen maximal zwei Punkte sein.
	 * @param str�mungsgebiet Gebiet, zu dem die Schnittpunkte mit der Route gesucht werden.
	 */
	public void sucheSchnittpunkte(Gebiet str�mungsgebiet){
		//Grenzpunkte des Gebietes
		float x1=str�mungsgebiet.getEckpunktA().getX();
		float x2=str�mungsgebiet.getEckpunktC().getX();
		float y1=str�mungsgebiet.getEckpunktA().getY();
		float y2=str�mungsgebiet.getEckpunktC().getY();
		//
		int z�hler=0;
		float x,y;
		
		//wenn die Route senkrecht ist, werden keine senkrechten Seiten der Str�mungsgebiete �berpr�ft
		if(!dieRoute.istSenkrecht()){
			//linke Seite des Gebiets
			if(dieRoute.routeAnStelleX(x1)!=null){
				y=dieRoute.routeAnStelleX(x1).getX();
				if(y>=y1&&y<=y2){
					schnittpunkte_tmp[z�hler]=dieRoute.routeAnStelleX(x1);
					z�hler+=1;
				}
			}
			
			//rechts Seite des Gebiets
			if(dieRoute.routeAnStelleX(x2)!=null){
				y=dieRoute.routeAnStelleX(x2).getX();
				if(y>=y1&&y<=y2){
					schnittpunkte_tmp[z�hler]=dieRoute.routeAnStelleX(x2);
					z�hler+=1;
				}
			}
		}
		//Abbruchbedingung (es k�nnen maximal 2 Seiten eines Gebietes geschnitten werden)
		if(z�hler==2){
			return;
		}
		//wenn die Route wagerecht ist, werden keine wagerechten Seiten der Str�mungsgebiete �berpr�ft
		if(!dieRoute.istWagerecht()){
			//untere Seite des Gebiets
			if(dieRoute.routeAnStelleY(y1)!=null){
				x=dieRoute.routeAnStelleY(y1).getX();
				if(x>=x1&&x<=x2){
					//Falls der Punkt ein Eckpunkt ist, k�nnte er bereits hinzugef�gt worden sein
					if(!tmpSchnittpunkteEnth�lt(dieRoute.routeAnStelleY(y1))){
						schnittpunkte_tmp[z�hler]=dieRoute.routeAnStelleY(y1);
						z�hler+=1;
					}
				}
			}
			//Abbruchbedingung (es k�nnen maximal 2 Seiten eines Gebietes geschnitten werden)
			if(z�hler==2){
				return;
			}
			//obere Seite des Gebiets
			if(dieRoute.routeAnStelleY(y2)!=null){
				x=dieRoute.routeAnStelleY(y2).getX();
				if(x>=x1&&x<=x2){
					//Falls der Punkt ein Eckpunkt ist, k�nnte er bereits hinzugef�gt worden sein
					if(!tmpSchnittpunkteEnth�lt(dieRoute.routeAnStelleY(y2))){
						schnittpunkte_tmp[z�hler]=dieRoute.routeAnStelleY(y2);
						z�hler+=1;
					}
				}
			}
			
		}
		
	}
	
	/**
	 * �berpr�ft, ob das Feld @param schnittpunkte_tmp einen Vektor enth�lt.
	 * @param v zu �berpr�fender Vektor
	 * @return
	 */
	public boolean tmpSchnittpunkteEnth�lt(Vektor v){
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
			//durchl�uft schnittpunkte komplett
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
		//schnittpunkte erth�lt alle vorherigen Punkte in sortierter Reihenfolge aus tmp
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
		Vektor anfang, ende, mittelpunkt, str�mung, entfernungsVektor, skv,rkv;
		float fahrzeit, entfernung,geschwindigkeit;
		str�mung=new Vektor(0,0);
		//ein Abschnitt besteht jeweils aus 2 Schnittpunkten
		for(int i=0;i<schnittpunkte.size()-1;i++){
			anfang=schnittpunkte.get(i);
			ende=schnittpunkte.get(i+1);
			//der Mittelpunkt des Abschnitts dient der Bestimmung des Str�mungsgebietes
			mittelpunkt=new Vektor(anfang.getX()+(ende.getX()-anfang.getX())/2,anfang.getY()+(ende.getY()-anfang.getY())/2);
			str�mung.set(0,0);
			//Bestimmung der Str�mung f�r den Abschnitt
			for(int j=str�mungen.size()-1;j>=0;j--){
				if(str�mungen.get(j).getStr�mungsGebiet().enth�ltPunkt(mittelpunkt)){
					//Str�mungsvektor
					str�mung.set(str�mungen.get(j).getStr�mungsVektor().getX(),(str�mungen.get(j).getStr�mungsVektor().getY()));
					break;
				}
			}
			//Berechnung der Daten des Abschnittes
			entfernungsVektor = new Vektor(ende.getX()-anfang.getX(),ende.getY()-anfang.getY());
			entfernung=entfernungsVektor.getNorm();
			//resultierender Kursvektor
			rkv=new Vektor(5/entfernungsVektor.getNorm()*entfernungsVektor.getX(),5/entfernungsVektor.getNorm()*entfernungsVektor.getY());
			//gesteuerter Kursvektor
			skv=new Vektor(rkv.getX()-str�mung.getX(),rkv.getY()-str�mung.getY());
			geschwindigkeit=skv.getNorm();
			fahrzeit=entfernung/geschwindigkeit;
						
			//erzeugt einen neuen Abschnitt und speichert diesen in der ArrayList abschnitte 
			neuerAbschnitt=new Abschnitt(anfang,ende,str�mung,entfernung,skv,fahrzeit,geschwindigkeit);
			abschnitte.add(neuerAbschnitt);	
		}

	}
	
	/**
	 * K�rzt die Werte aller Abschnitte. Dies dient der leserlicheren Ausgabe.
	 */
	public void k�rzeAbschnittsWerte(){
		Abschnitt a;
		for(int i=0;i<abschnitte.size();i++){
			a=abschnitte.get(i);
			a.setAnfang(new Vektor(k�rze(a.getAnfang().getX(),1),k�rze(a.getAnfang().getY(),1)));
			a.setEnde(new Vektor(k�rze(a.getEnde().getX(),1),k�rze(a.getEnde().getY(),1)));
			a.setStr�mungsVektor(new Vektor(k�rze(a.getStr�mungsVektor().getX(),1),k�rze(a.getStr�mungsVektor().getY(),1)));
			a.setGesteuerterVektor(new Vektor(k�rze(a.getGesteuerterVektor().getX(),1),k�rze(a.getGesteuerterVektor().getY(),1)));
			a.setEntfernung(k�rze(a.getEntfernung(),1));
			a.setFahrzeit(k�rze(a.getFahrzeit(),1));
			a.setGeschwindigkeit(k�rze(a.getGeschwindigkeit(),1));
		}
	}
	
	/**
	 * Methode zum K�rzen von Float-Werten
	 * @param zahl zu k�rzender Wert
	 * @param stellen Anzahl der Nachkommastellen
	 * @return
	 */
	public static float k�rze(float zahl, int stellen){
		float genauigkeit=1.0F;
		
		genauigkeit*=Math.pow(10, stellen);
		return((int) (zahl*genauigkeit+0.5)/genauigkeit);
	}
	
	/*
	 * Getter- und Setter-Methoden
	 */
	
	/**
	 * Gibt das Gebiet zur�ck.
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
	 * Gibt die Route zur�ck.
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
	 * Gibt die Abschnitte f�r die Route zur�ck.
	 * @return die Abschnitte f�r die Route
	 */
	public ArrayList <Abschnitt> getAbschnitte() {
		return abschnitte;
	}

	/**
	 * Setzt die Abschnitte f�r die Route.
	 * @param abschnitte neue Abschnitte f�r die Route
	 */
	public void setAbschnitte(ArrayList <Abschnitt> abschnitte) {
		this.abschnitte = abschnitte;
	}

	/**
	 * Setzt die Str�mungen.
	 * @param str�mungen neue Str�mungen
	 */
	public void setStr�mungen(ArrayList <Str�mung> str�mungen){
		this.str�mungen=str�mungen;		
	}
	
	/**
	 * Gibt die Str�mungen zur�ck.
	 * @return die Str�mungen
	 */
	public ArrayList <Str�mung> getStr�mungen(){
		return this.str�mungen;
	}
	
	/**
	 * Setzt die Schnittpunkte der Route.
	 * @param schnittpunkte neue Schnittpunkte der Route
	 */
	public void setSchnittpunkte(ArrayList <Vektor> schnittpunkte){
		this.schnittpunkte=schnittpunkte;		
	}
	
	/**
	 * Gibt die Schnittpunkte der Route zur�ck.
	 * @return Schnittpunkte der Route
	 */
	public ArrayList <Vektor> getSchnittpunkte(){
		return this.schnittpunkte;
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
	 * Gibt den Namen der Eingabe-Datei zur�ck.
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
	 * Gibt die Eingabe zur�ck.
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
