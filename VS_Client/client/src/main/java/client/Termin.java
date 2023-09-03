package client;
import java.util.Date;

public class Termin {
	private int terminId;
	private String titel;
	private Date datum; //(int year, int month, int date, int hrs, int min)
	private int dauer;
	private int idErsteller;
	private String benutzerEingeladen;
	
	//Konstruktor mit allen Parametern
	public Termin(int terminId, String titel, Date datum, int dauer, int idErsteller, String benutzerEingeladen) {
		this.terminId = terminId;
		this.titel = titel;
		this.datum = datum;
		this.dauer = dauer;
		this.idErsteller = idErsteller;
		this.benutzerEingeladen = benutzerEingeladen;
	}
	
	//Konstruktor ohne terminId für neu erstellte Termine
		public Termin(String titel, Date datum, int dauer, int idErsteller, String benutzerEingeladen) {
			this.titel = titel;
			this.datum = datum;
			this.dauer = dauer;
			this.idErsteller = idErsteller;
			this.benutzerEingeladen = benutzerEingeladen;
		}
	

	public void benutzerEinladen(String benutzername){
		this.benutzerEingeladen = this.benutzerEingeladen+","+benutzername;

	}
	
	public int getTerminId() {
		return terminId;
	}
	
	public String gettitel() {
		return titel;
	}
	
	public Date getDatum() {
		return datum;
	}
	
	public int getDauer() {
		return dauer;
	}
	
	public int getIdErsteller() {
		return idErsteller;
	}
	
	
	public String getBenutzerEingeladen() {
		return benutzerEingeladen;
	}
}
