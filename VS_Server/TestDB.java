import java.sql.ResultSet;
import java.sql.SQLException;

public class TestDB {
	public static void main(String[] args){
		DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		
		db.oeffneDB();
		Benutzer benutzer = new Benutzer("1234", "Baldauf", "Niklas", 1);
		db.insertBenutzer(benutzer);
		ResultSet a = db.sucheBenutzerIdMitName("Baldauf");
		
		try {
			while(a.next()) {
				System.out.print(a.getInt(1) + "\n");
				System.out.print(a.getString(2)+ "\n");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		db.schliesseDB();
    }
}
