package client;
import java.time.LocalDateTime;


public class Termin {
	private int terminId;
	private String titel;
	private String datum; //(int year, int month, int date, int hrs, int min)
	private int dauer;
	private int idErsteller;
	private String benutzerEingeladen;
	
	//default 
		public Termin() {}
	
	//Konstruktor mit allen Parametern
	public Termin(int terminId, String titel, String datum, int dauer, int idErsteller, String benutzerEingeladen) {
		this.terminId = terminId;
		this.titel = titel;
		this.datum = datum;
		this.dauer = dauer;
		this.idErsteller = idErsteller;
		this.benutzerEingeladen = benutzerEingeladen;
	}
	
	//Konstruktor ohne terminId für neu erstellte Termine
		public Termin(String titel, String datum, int dauer, int idErsteller, String benutzerEingeladen) {
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
	
	public String getDatum() {
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

	public void setTerminId(int terminId) {
		this.terminId = terminId;
	}	
	
	public void setTitel(String titel) {
		this.titel = titel;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}
	
	public void setIdErsteller(int id) {
		this.idErsteller = id;
	}
	
	@Override
	public String toString() {
		return "Termin [terminId=" + terminId + ", titel=" + titel + ", datum=" + datum + ", dauer=" + dauer
				+ ", idErsteller=" + idErsteller + ", benutzerEingeladen=" + benutzerEingeladen + "]";
	}
	
}
