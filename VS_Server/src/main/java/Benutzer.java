import com.fasterxml.jackson.annotation.JsonProperty;

public class Benutzer {
	//Attribute
	@JsonProperty("benutzerId")
	private int benutzerId;
	
	@JsonProperty("benutzerName")
	private String benutzerName;
	
	@JsonProperty("passwort")
	private String passwort;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("vorname")
	private String vorname;
	
	@JsonProperty("isAdmin")
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
	
	//Konstruktor ohne BenutzerId, wird bei neu erstellten personen benötigt
	public Benutzer(String benutzerName, String passwort, String name, String vorname, int isAdmin) {
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.isAdmin = isAdmin;
	}
	
	//Konstruktor nur BenutzerID + isAdmin
	public Benutzer(int benutzerId, int isAdmin) {
		this.benutzerId = benutzerId;
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

}
