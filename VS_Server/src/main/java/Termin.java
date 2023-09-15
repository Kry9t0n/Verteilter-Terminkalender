import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Termin {
	@JsonProperty("terminId")
	private int terminId;
	
	@JsonProperty("titel")
	private String titel;
	
	@JsonProperty("datum")
	private String datum; //(int year, int month, int date, int hrs, int min)
	
	@JsonProperty("dauer")
	private int dauer;
	
	@JsonProperty("idErsteller")
	private int idErsteller;
	
	@JsonProperty("benutzerEingeladen")
	private String benutzerEingeladen;
	
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
	
	public int getTerminId() {
		return terminId;
	}
	
	public String getTitel() {
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
}
