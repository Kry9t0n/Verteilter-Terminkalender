
public class Benutzer {
	//Attribute
	private int benutzerId;
	private String benutzerName;
	private String passwort;
	private String name;
	private String vorname;
	private boolean isAdmin;
	
	//Konstruktor mit allen Parametern
	public Benutzer(int benutzerId, String benutzerName, String passwort, String name, String vorname, boolean isAdmin) {
		this.benutzerId = benutzerId;
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	//Konstruktor ohne BenutzerId, wird bei neu erstellten personen benötigt
		public Benutzer(String benutzerName, String passwort, String name, String vorname, boolean isAdmin) {
			this.benutzerName = benutzerName;
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
	
	
	public boolean getIsAdmin() {
		return isAdmin;
	}

}
