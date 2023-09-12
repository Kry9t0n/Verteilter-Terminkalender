package client;

public class Benutzer {
	// Attribute
	private int benutzerId;
	private String benutzerName;
	private String passwort;
	private String name;
	private String vorname;
	private int isAdmin;
	
	//Standard-Konstruktor
	// alle Attribute werden auf Null gesetzt
	public Benutzer() {}
	
	// Konstruktor ohne BenutzerId und Benutzername wird bei neu erstellten personen
	// benötigt
	public Benutzer(String passwort, String name, String vorname, int isAdmin) {
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}

	// Konstruktor mit allen Parametern
	public Benutzer(int benutzerId, String benutzerName, String passwort, String name, String vorname, int isAdmin) {
		this.benutzerId = benutzerId;
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	public void setBenutzerId(int benutzerId) {
		this.benutzerId = benutzerId;
	}


	public void setBenutzerName(String benutzerName) {
		this.benutzerName = benutzerName;
	}


	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setVorname(String vorname) {
		this.vorname = vorname;
	}


	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}


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

	@Override
	public String toString() {
		return "Benutzer [benutzerId=" + benutzerId + ", benutzerName=" + benutzerName + ", passwort=" + passwort
				+ ", name=" + name + ", vorname=" + vorname + ", isAdmin=" + isAdmin + "]";
	}
	
	
}
