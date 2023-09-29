package vs.terminkalender.datatypes;

import vs.terminkalender.database.DB_Funktionen;

/**
 * @Autor Niklas Baldauf, Maik Gierlinger
 * @version 1.0
 * @see DB_Funktionen
 */
public class Termin {
	
	private int terminId;
	private String titel;
	private String datum; //Format wird von Client Team festgelegt
	private int dauer;
	private int idErsteller;
	private String benutzerEingeladen;
	
	/**
	 * Konstruktor mit allen Atributen
	 * @param terminId
	 * @param titel
	 * @param datum
	 * @param dauer
	 * @param idErsteller
	 * @param benutzerEingeladen
	 */
	public Termin(int terminId, String titel, String datum, int dauer, int idErsteller, String benutzerEingeladen) {
		this.terminId = terminId;
		this.titel = titel;
		this.datum = datum;
		this.dauer = dauer;
		this.idErsteller = idErsteller;
		this.benutzerEingeladen = benutzerEingeladen;
	}
	
	/**
	 * Konstruktor ohne TerminID
	 * @param titel
	 * @param datum
	 * @param dauer
	 * @param idErsteller
	 * @param benutzerEingeladen
	 */
		public Termin(String titel, String datum, int dauer, int idErsteller, String benutzerEingeladen) {
			this.titel = titel;
			this.datum = datum;
			this.dauer = dauer;
			this.idErsteller = idErsteller;
			this.benutzerEingeladen = benutzerEingeladen;
		}
		
		public Termin() {}
	
	/**	
	 * Get Methoden
	 */
	
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
	
	/**
	 * Set Methoden
	 */
	
	public void setTerminId(int eingabe) {
		terminId = eingabe;
	}
	
	public void setTitel(String eingabe) {
		titel = eingabe;
	}
	
	public void setDatum(String eingabe) {
		datum = eingabe;
	}
	
	public void setDauer(int eingabe) {
		dauer = eingabe;
	}
	
	public void setIdErsteller(int eingabe) {
		idErsteller = eingabe;
	}
	
	public void setBenutzerEingeladen(String eingabe) {
		benutzerEingeladen = eingabe;
	}
	
	@Override
	public String toString() {
		return "Termin [terminId=" + terminId + ", titel=" + titel + ", datum=" + datum + ", dauer=" + dauer
				+ ", idErsteller=" + idErsteller + ", benutzerEingeladen=" + benutzerEingeladen + "]";
	}

}
