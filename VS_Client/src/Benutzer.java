
public class Benutzer {
	//Attribute
	private int benutzerId;
	private String benutzerName;
	private String passwort;
	private String name;
	private String vorname;
	private int isAdmin;
	
	//Konstruktor mit allen Parametern
	public Benutzer(int benutzerId, String benutzerName, String passwort, String name, String vorname, int isAdmin) {
		this.benutzerId = benutzerId;
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	//Konstruktor ohne BenutzerId und Benutzername wird bei neu erstellten personen benötigt
		public Benutzer(String passwort, String name, String vorname, int isAdmin) {
			this.passwort = passwort;
			this.name = name;
			this.vorname = vorname;
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

}
