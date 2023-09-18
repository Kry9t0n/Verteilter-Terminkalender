import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
/**
 * @Autor Niklas Baldauf, Maik Girlinger
 * @version 1.0
 * @see DB_Funktionen
 */
public class OnlineCheck {
	/**
	 * @param ZEIT_INTERVALL in Milisekunden (600000 = 10 min)
	 */
	private static final int ZEIT_INTERVALL = 600000; 
	private Timer timer = new Timer();

	/**
	 * Main Methode
	 * @param args
	 */
    public static void main(String[] args) {
    	OnlineCheck check = new OnlineCheck();
        check.startTask();
    }

    /**
     * Startet alle 10 Minuten die Klasse Pruefe
     */
    public void startTask() {
        timer.scheduleAtFixedRate(new Pruefe(), 0, ZEIT_INTERVALL);
    }

    /**
     * Die Klasse Prüfe enthällt alle Funktionen für die Prüfung des Online Status
     */
    private class Pruefe extends TimerTask {
    	/**
    	 * @param VERGLEICHS_ZEIT gibt die zeot an mit der verglichen wird, das sind 30 Minuten
    	 */
        private static final int VERGLEICHS_ZEIT = 30;

        /**
         * Prüfunktiopn die die Daten aus der Tabelle ONLINE abruft und dann die einzelenen Einträge mit dern vergleichzeit vergleicht und gegebenfals löscht
         */
		@Override
        public void run() {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
        	db.oeffneDB();
        	int zeit = db.aktuelleZeit();     	
    		ResultSet a = db.prueffeOnlineEintraege();
    		if(zeit > 031 & zeit < 2359) {
    			try {
    				ArrayList<Integer> list = new ArrayList<Integer>();
    				while(a.next()) {
    					list.add(a.getInt(1));
    					list.add(a.getInt(2));
    				}
    				for(int i=1; i<list.size(); i=i+2) {
    					if(Math.abs(zeit - list.get(i)) > VERGLEICHS_ZEIT) {
    						db.loescheOnlineEintrag(list.get((i-1)));
    					}
    				}
    				} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		db.schliesseDB();
    		timer.cancel();
    		}
        }
    }
}
