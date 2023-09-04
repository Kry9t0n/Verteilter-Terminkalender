import java.sql.*;

public class DB_Tabelle_Zugriff {
	String url;
	String user;
	String password;
	
	Connection conn = null;
	Statement stmtSQL = null;
	ResultSet rs = null;
	
	public DB_Tabelle_Zugriff(String user, String password) {
		this.url = "jdbc:sqlite:Datenbank.db";
		this.user = user;
		this.password = password;
	}
	
	/*TO D
	 * 
	 * Benutzer:
	 * -ändern anhand ID
	 * 
	 * Termin:
	 * -Termin anhand ID finden, alles ausgeben
	 * -Termin anhand BenutzerID finden, alle mit allem ausgeben
	 * -Termin ändern anhand ID
	 * -Termin löschen anhand ID
	 * 
	 * Online:
	 * -Eintrag erstellen 
	 * -Eintrag ändern anhand BenutzerID 
	 * -Eintrag löschen anhand BenutzerID
	 * 
	 * Eingeladen:
	 * -Eintrag erstellen
	 * -Eintrag ändern anhand BenutzerID und TerminID
	 * -Eintrag löschen anhand BenutzerID und TerminID
	 * -TerminIDs ausgeben anhand BenutzerID
	 */

	public void oeffneDB() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e){
			System.err.println("Fehler beim Laden des JDBC-Treibers");
			System.out.print(e);
		}
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			stmtSQL = conn.createStatement();
			System.out.println("Connection etabliert!");
		} catch (SQLException e){
			System.out.print(e);
		}
	}
	
	public void schliesseDB() {
		try {stmtSQL.close();
			conn.close();
			System.out.println("Connection geschlossen!");
		}
		catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	//Benutzer Funktionen
	
	public String erstelleBenutzer(Benutzer benutzer) {
		String benutzername = erstelleBenutzernamen(benutzer); //Erstellt aus Name + Vorname einen Benutzernamen
		
		String sql = "INSERT INTO BENUTZER (BENUTZERNAME, PASSWORT, NAME ,VORNAME, ISADMIN) "+ 
				"VALUES('"+ benutzername + "','" + benutzer.getPasswort() + "','"+ 
				benutzer.getName() +"','"+ benutzer.getVorname() +"',"+ benutzer.getIsAdmin()+ ");";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
		return benutzername;
	}
	
	public ResultSet sucheBenutzerIdMitName(String name) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME FROM BENUTZER WHERE NAME = '" + name + "';");
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	public ResultSet benutzerAuthentifkation(String benutzerName, String passwort) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, ISADMIN FROM BENUTZER WHERE BENUTZERNAME = '" + benutzerName + "' AND PASSWORT = '" + passwort + "';");
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	public void löscheBenutzer(int benutzerId) {
		try {
			stmtSQL.executeUpdate("DELETE FROM BENUTZER WHERE BENUTZERID = '" + benutzerId + "';");
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	public String erstelleBenutzernamen(Benutzer benutzer) {
		String benutzername = benutzer.getName()+ benutzer.getVorname().substring(0,3);
		benutzername = benutzername.toLowerCase();
		return benutzername;
	}
	
	//Termin Funktionen
	
	public void erstelleTermin(Termin termin) {
		
		String sql = "INSERT INTO TERMIN (TITEL, DATUM, DAUER, IDERSTELLER, BENUTZEREINGELADEN) "+ 
				"VALUES('"+ termin.getTitel() + "','" + termin.getDatum() + "','"+ 
				termin.getDauer() +"','"+ termin.getIdErsteller() +"',"+ termin.getBenutzerEingeladen()+ ");";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
}
