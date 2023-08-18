import java.sql.*;

public class DB_Tabelle_Zugriff {
	String url;
	String user;
	String password;
	
	Connection conn = null;
	Statement stmtSQL = null;
	ResultSet rs = null;
	
	public DB_Tabelle_Zugriff(String user, String password) {
		this.url = "";
		this.user = user;
		this.password = password;
	}

	public void oeffneDB() {
		try {
			Class.forName("");
		} catch (ClassNotFoundException e){
			System.out.print(e);
		}
		
		try {
			DriverManager.getConnection(url, user, password);
			stmtSQL = conn.createStatement();
			System.out.print("Connection etabliert!");
		} catch (SQLException e){
			System.out.print(e);
		}
	}
	
	public ResultSet sucheBenutzerID(int id) {
		try {
			rs = stmtSQL.executeQuery("SELECT BenutzerID FROM Benutzer WHERE BenutzerID = " + id);
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	public void schliesseDB() {
		try {stmtSQL.close();
			conn.close();
		}
		catch (SQLException e) {
			System.err.println(e);
		}
	}
}
