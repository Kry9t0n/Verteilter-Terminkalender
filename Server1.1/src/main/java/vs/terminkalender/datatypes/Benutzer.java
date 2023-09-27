package vs.terminkalender.datatypes;

import vs.terminkalender.database.DB_Funktionen;

/**
 * @Autor Niklas Baldauf, Maik Girlinger, Niklas Balke, Justin Witsch
 * @version 1.1
 * @see DB_Funktionen
 */
public class Benutzer {
	
	/**
	 * Attribute
	 */
	private int benutzerId;
	private String benutzerName;
	private String passwort;
	private String name;
	private String vorname;
	private int isAdmin;
	
	/**
	 * Standard-Konstruktor alle Attribute werden auf Null gesetzt
	 */
	public Benutzer() {}
	
	/**
	 * Konstruktor mit allen Atributen
	 * @param benutzerId
	 * @param benutzerName
	 * @param passwort
	 * @param name
	 * @param vorname
	 * @param isAdmin
	 */
	public Benutzer(int benutzerId, String benutzerName, String passwort, String name, String vorname, int isAdmin) {
		this.benutzerId = benutzerId;
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	/**
	 * Konstruktor ohne BenutzerID
	 * @param passwort
	 * @param name
	 * @param vorname
	 * @param isAdmin
	 */
	public Benutzer(String passwort, String name, String vorname, int isAdmin) {
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	/**
	 * Get Methoden
	 */
	public int getBenutzerId() {
		return benutzerId;
	}
	
	public String getBenutzerName() {
		return benutzerName;
	}
	
	public String getPasswort() {
		return passwort;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVorname() {
		return vorname;
	}
	
	
	public int getIsAdmin() {
		return isAdmin;
	}
	
	/**
	 * Set Methoden
	 */
	public void setBenutzerId(int eingabe) {
		benutzerId = eingabe;
	}
	
	public void setBenutzerName(String eingabe) {
		benutzerName = eingabe;
	}
	
	public void setPasswort(String eingabe) {
		passwort = eingabe;
	}
	
	public void setName(String eingabe) {
		name = eingabe;
	}
	
	public void setVorname(String eingabe) {
		vorname = eingabe;
	}
	
	public void setIsAdmin(int eingabe) {
		isAdmin = eingabe;
	}

	/**
	 * die toString Methode gibt das Objekt als String zurück
	 */
	@Override
	public String toString() {
		return "Benutzer [benutzerId=" + benutzerId + ", benutzerName=" + benutzerName + ", passwort=" + passwort
				+ ", name=" + name + ", vorname=" + vorname + ", isAdmin=" + isAdmin + "]";
	}
	
}
