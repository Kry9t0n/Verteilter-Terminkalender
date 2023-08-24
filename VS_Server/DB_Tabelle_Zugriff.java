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
	
	/*TO DO
	 * Benutzer:
	 * -ändern anhand ID
	 * -Mit BName und PWort, ID + isAdmin ausgeben (Login)
	 * -Benutzer löschen anhand ID
	 * 
	 * Termin:
	 * -Termin erstellen
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
	
	//Funktionen
	
	public ResultSet sucheBenutzerIdMitName(String name) {
		try {
			rs = stmtSQL.executeQuery("SELECT BenutzerID, Benutzername FROM Benutzer WHERE Name = '" + name + "';");
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	public void insertBenutzer(Benutzer benutzer) {
		String benutzername = erstelleBenutzernamen(benutzer);
		String sql = "INSERT INTO Benutzer (BenutzerName, Passwort, Name ,Vorname, IsAdmin) "+ 
				"VALUES('"+ benutzername + "','" + benutzer.getPasswort() + "','"+ 
				benutzer.getName() +"','"+ benutzer.getVorname() +"',"+ benutzer.getIsAdmin()+ ");";
		try {
			stmtSQL.executeQuery(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	public String erstelleBenutzernamen(Benutzer benutzer) {
		String benutzername = benutzer.getName()+ benutzer.getVorname().substring(0,3);
		benutzername = benutzername.toLowerCase();
		return benutzername;
	}
}
