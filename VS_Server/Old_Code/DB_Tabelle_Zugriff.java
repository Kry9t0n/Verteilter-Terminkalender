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
	 * 		-Eintrag erstellen
	 * 		-Eintrag ändern anhand BenutzerID und TerminID
	 * 		-Eintrag löschen anhand BenutzerID und TerminID
	 * 		-TerminIDs ausgeben anhand BenutzerID
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
	
	public ResultSet sucheBenutzerIdMitBenutzername(String benutzername) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME FROM BENUTZER WHERE BENUTZERNAME = '" + benutzername + "';");
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
	
	public void erstelleTerminUndEintragEingeladenAnhandBenutzerID(Termin termin) {
		
		String sql = "INSERT INTO TERMIN (TITEL, DATUM, DAUER, IDERSTELLER) "+ 
				"VALUES('"+ termin.getTitel() + "','" + termin.getDatum() + "','"+ 
				termin.getDauer() +"','"+ termin.getIdErsteller() + "');";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		
		String sql2 = "SELECT TERMINID FROM TERMIN WHERE TITEL = '" + termin.getTitel() + "' AND DATUM = '" + termin.getDatum() + "' AND DAUER = " + termin.getDauer() + " AND IDERSTELLER = " + termin.getIdErsteller() + ";";
		try {
			rs = stmtSQL.executeQuery(sql2);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		int terminid = -1;
		try {
			terminid = rs.getInt(1);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		//Splitet die eingeladenen Benutzer beim , und schreibt sie in ein String-Array
		String array[] = termin.getBenutzerEingeladen().split(",");
		
		if(terminid != -1) {	
			String sql3 = "";
			for(String s : array) {
				sql3 = "INSERT INTO Eingeladen (BENUTZERID, TERMINID) "+ 
					"VALUES("+ s + "," + terminid + ");";
				try {
					stmtSQL.executeUpdate(sql3);
				} catch(SQLException err) {
					System.err.println(err);
				} 
			}
		}
	}
	
	public void erstelleTerminUndEintragEingeladenAnhandBenutzername(Termin termin) {
		
		String sql = "INSERT INTO TERMIN (TITEL, DATUM, DAUER, IDERSTELLER) "+ 
				"VALUES('"+ termin.getTitel() + "','" + termin.getDatum() + "','"+ 
				termin.getDauer() +"','"+ termin.getIdErsteller() + "');";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		
		String sql2 = "SELECT TERMINID FROM TERMIN WHERE TITEL = '" + termin.getTitel() + "' AND DATUM = '" + termin.getDatum() + "' AND DAUER = " + termin.getDauer() + " AND IDERSTELLER = " + termin.getIdErsteller() + ";";
		try {
			rs = stmtSQL.executeQuery(sql2);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		int terminid = -1;
		try {
			terminid = rs.getInt(1);
		} catch(SQLException err) {
			System.err.println(err);
		}
		
		//Splitet die eingeladenen Benutzer beim , und schreibt sie in ein String-Array
		String arrayBenutzername[] = termin.getBenutzerEingeladen().split(",");
		int anz = arrayBenutzername.length;
		int[] arrayID = new int[anz];
		
		int i = 0;
		for(String s : arrayBenutzername) {
			try {
				rs = sucheBenutzerIdMitBenutzername(s);
				arrayID[i] = rs.getInt(1);
				i++;
			} catch(SQLException err) {
				System.err.println(err);
			}
		}
		
		if(terminid != -1) {	
			String sql3 = "";
			for(int benutzerid : arrayID) {
				sql3 = "INSERT INTO Eingeladen (BENUTZERID, TERMINID) "+ 
					"VALUES("+ benutzerid + "," + terminid + ");";
				try {
					stmtSQL.executeUpdate(sql3);
				} catch(SQLException err) {
					System.err.println(err);
				} 
			}
		}
	}
	
	
	//Eingeladen Funktionen
	
	public void erstelleEintragEingeladen(int benutzerId, int terminId, String info) {
		
		String sql = "INSERT INTO Eingeladen (BENUTZERID, TERMINID, Info) "+ 
				"VALUES( "+ benutzerId + " , " + terminId + " , '"+ 
				info + "');";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	public void aendernEintragAnhandBenutzerIdTerminId(int benutzerId, int terminId, String info, int neuBenutzerId, int neuTerminId)  {
		
		String sql = "UPDATE Eingeladen SET BENUTZERID = " + benutzerId + ", TERMINID = " + terminId + ", INFO = '" + info + "' WHERE BENUTZERID = " + neuBenutzerId + " and TERMINID = " + neuTerminId + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	public void loescheEintragEingeladenAnhandBenutzerIdTerminId(int benutzerId, int terminId) {
		
		String sql = "DELETE FROM Eingeladen WHERE BENUTZERID = " + benutzerId + " and TERMINID = " + terminId + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	public ResultSet ausgabenEintragAnhandBenutzerId(int benutzerId) {
		
		String sql = "SELECT * FROM Eingeladen WHERE BENUTZERID = " + benutzerId + ";";
		try {
			rs = stmtSQL.executeQuery(sql);
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	public ResultSet ausgabenAlleEintraege() {
		
		String sql = "SELECT * FROM Eingeladen;";
		try {
			rs = stmtSQL.executeQuery(sql);
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
}
