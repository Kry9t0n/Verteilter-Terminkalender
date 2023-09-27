package vs.terminkalender.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import vs.terminkalender.datatypes.Benutzer;
import vs.terminkalender.datatypes.Termin;

/**
 * @Autor Niklas Baldauf, Maik Gierlinger
 * @version 1.0
 * @see Termin, Benutzer, OnlineCheck
 */
public class DB_Funktionen {
	
	/**
	 * Parameter der Klasse
	 * @param url = Der Speicherort der Datenbank.
	 * @param user = Benutzername in unserem fall "SA"
	 * @param password = Das Passwort für die DB, in unserem Fall gibt es keines -> ""
	 */
	String url;
	String user;
	String password;
	
	Connection conn = null;
	Statement stmtSQL = null;
	ResultSet rs = null;
	
	private final Benutzer failedBenutzer = new Benutzer(-1,"-1","-1","-1","-1",-1);
	private final Termin failedTermin = new Termin(-1,"-1","-1",-1,-1,"-1");
	
	/**
	 * Konstruktor
	 * @param user
	 * @param password
	 */
	public DB_Funktionen(String user, String password) {
		String url = "jdbc:sqlite:"+ System.getProperty("user.home") +  "/eclipse-workspace/Server1.1/Datenbank.db";
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	/*TO DO
	 * 
	 * -Fehlerbehandlung!!!!!
	 *
	 */

	/**
	 * Öffnet die Datenbank Verbindung
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
	
	/**
	 * Schließt die verbung zur Datenbank
	 */
	public void schliesseDB() {
		try {stmtSQL.close();
			conn.close();
			System.out.println("Connection geschlossen!");
		}
		catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Benutzer Funktionen
	 */
	
	/**
	 * Erstellt einen Benutzer in der Tabelle BENUTZER
	 * @param benutzer Ein benutzerobjekt muss übergeben werden
	 */
	public void erstelleBenutzer(Benutzer benutzer) {
		String benutzername = erstelleBenutzernamen(benutzer); //Erstellt aus Name + Vorname einen Benutzernamen
		
		String sql = "INSERT INTO BENUTZER (BENUTZERNAME, PASSWORT, NAME ,VORNAME, ISADMIN) "+ 
				"VALUES('"+ benutzername + "','" + benutzer.getPasswort() + "','"+ 
				benutzer.getName() +"','"+ benutzer.getVorname() +"',"+ benutzer.getIsAdmin()+ ");";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * sucht einen Benutzer anhand seines Nachnamen in der Tabelle BENUTZER
	 * @param name
	 * @return ein Benutzer Objekt
	 */
	public Benutzer sucheBenutzerIdMitName(String name) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME FROM BENUTZER WHERE NAME = '" + name + "';");
			Benutzer benutzer = ResultSetToBenutzer(rs);
			return benutzer;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Sucht einen Benutzer anhand des Benutzernamen in der Tabelle BENUTZER
	 * @param benutzername
	 * @return ein Result Set
	 */
	public ResultSet sucheBenutzerIdMitBenutzername(String benutzername) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME FROM BENUTZER WHERE BENUTZERNAME = '" + benutzername + "';");
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Sucht einen Benutzer anhand der BenutzerID in der Tabelle BENUTZER
	 * @param benutzername
	 * @return ein Result Set
	 */
	public Benutzer sucheBenutzerMitBenutzerId(int benutzerId) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME FROM BENUTZER WHERE BENUTZERID = " + benutzerId + ";");
			Benutzer benutzer = ResultSetToBenutzer(rs);
			return benutzer;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Sucht einen Benutzer anhand der BenutzerId in der Tabelle BENUTZER
	 * @param benutzerId
	 * @return Benutzer-Objekt
	 */
	public ArrayList<Benutzer> sucheAlleBenutzerMitTerminIdAusEingeladen(int terminId) {
		ArrayList<Benutzer> list = new ArrayList<Benutzer>();
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME, PASSWORT, NAME, VORNAME, ISADMIN FROM BENUTZER NATURAL JOIN EINGELADEN WHERE TERMINID = " + terminId + ";");
			while(rs.next()) {
				list.add(ResultSetToBenutzer(rs));
			}
			return list;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
     * Gibt alle Benutzer mit allen Daten in der Tabelle BENUTZER
     * @return ArrayList<Benutzer> aller Benutzer
     */
    public ArrayList<Benutzer> gibAlleBenutzer() {
        try {
            rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME, PASSWORT, NAME, VORNAME, ISADMIN FROM BENUTZER;");
            ArrayList<Benutzer> list = new ArrayList<Benutzer>();
            while(rs.next()) {
                list.add(ResultSetToBenutzer(rs));
            }
            return list;
        } catch(SQLException err) {
            System.err.println(err);
            return null;
        }
    }
	
	/**
	 * Authentifiziert einen Benutzer anhand seines Benutzernamen und des Passwortes
	 * @param benutzerName
	 * @param passwort
	 * @return ein Benutzer Objekt
	 */
	public Benutzer benutzerAuthentifkation(String benutzerName, String passwort) {
		try {
			rs = stmtSQL.executeQuery("SELECT BENUTZERID, ISADMIN FROM BENUTZER WHERE BENUTZERNAME = '" + benutzerName + "' AND PASSWORT = '" + passwort + "';");
			Benutzer benutzer = ResultSetToBenutzer(rs);
			return benutzer;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Ändert die Daten eines Benutzers in der Tabelle BENUTZER indem er die Daten einfügt, die Datenbank ersetzt die Daten bei einem Konflikt der BenutzerID
	 * @param benutzer
	 */
	public void aendereBenutzer(Benutzer benutzer) {
		String sql = "UPDATE BENUTZER SET BENUTZERNAME = '" + benutzer.getBenutzerName() + "' ,PASSWORT = '" + benutzer.getPasswort() + "' ,NAME = '" + benutzer.getName() + "' ,Vorname = '" + benutzer.getVorname() + "' ,ISADMIN = " + benutzer.getIsAdmin() + " WHERE BENUTZERID = " + benutzer.getBenutzerId() + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Löscht einen Benutzer aus der Tabelle BENUTZER
	 * @param benutzerId
	 */
	public void loescheBenutzer(int benutzerId) {
		try {
			stmtSQL.executeUpdate("DELETE FROM BENUTZER WHERE BENUTZERID = '" + benutzerId + "';");
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Generiert einen Benutzernamern aus den Namen und den ersten 3 Buchstaben des Vornamen
	 * @param benutzer
	 * @return der generierte Benutzername
	 */
	public String erstelleBenutzernamen(Benutzer benutzer) {
		String benutzername = benutzer.getName()+ benutzer.getVorname().substring(0,3);
		benutzername = benutzername.toLowerCase();
		return benutzername;
	}
	
	/**
	 * Erstellt aus einem Result Set ein Benutzer Objekt, Defaultwerte sind -1
	 * @param a = das übergebene Result Set
	 * @return ein Benutzer Objekt
	 */
	public Benutzer ResultSetToBenutzer(ResultSet a) {
		//System.out.println("Hier2"); //ENTFERNEN??
		Benutzer benutzer = failedBenutzer; 
		try {
			benutzer.setBenutzerId(a.getInt("BENUTZERID"));
		} catch (SQLException e) {}
		try {
			benutzer.setBenutzerName(a.getString("BENUTZERNAME"));
		} catch (SQLException e) {}
		try {
			benutzer.setPasswort(a.getString("PASSWORT"));
		} catch (SQLException e) {}
		try {
			benutzer.setName(a.getString("NAME"));
		} catch (SQLException e) {}
		try {
			benutzer.setVorname(a.getString("VORNAME"));
		} catch (SQLException e) {}
		try {
			benutzer.setIsAdmin(a.getInt("ISADMIN"));
		} catch (SQLException e) {}
			
		return benutzer;
		}
	
	/**
	 * Termin Funktionen
	 */
	
	/**
	 * Erstellt einen Termin in der Tabelle TERMIN und einen Eintrag in der Tabelle EINGELADEN anhand der BenutzerId
	 * @param termin
	 */
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
	
	/**
	 * Erstellt einen Termin in der Tabelle TERMIN und einen Eintrag in der Tabelle EINGELADEN anhand des Benutzernamen
	 * @param termin
	 */
	public ArrayList<String> erstelleTerminUndEintragEingeladenAnhandBenutzerName(Termin termin) {
			
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
			ArrayList<Integer> arrayIDList = new ArrayList<Integer>();
			
			ArrayList<String> benutzerEinladungFailList = new ArrayList<String>();
			
			for(String s : arrayBenutzername) {
				try {
					rs = sucheBenutzerIdMitBenutzername(s);
					int help = rs.getInt(1);
					System.out.print(help);
					if(help != 0) {
						arrayIDList.add(help);
					} else {
						benutzerEinladungFailList.add(s);
					}
				} catch(SQLException err) {
					System.err.println(err);
				}
			}
			
			if(terminid != -1) {	
				String sql3 = "";
				for(int benutzerId : arrayIDList) {
					sql3 = "INSERT INTO Eingeladen (BENUTZERID, TERMINID) "+ 
						"VALUES("+ benutzerId + "," + terminid + ");";
					try {
						stmtSQL.executeUpdate(sql3);
					} catch(SQLException err) {
						System.err.println(err);
					} 
				}
			}
			return benutzerEinladungFailList;
	}
	
	/**
	 * Sucht einen Termin anhand der TerminID
	 * @param TerminId
	 * @return ein Termin Objekt
	 */
	public Termin sucheTerminMitTerminId(int TerminId) {
		try {
			rs = stmtSQL.executeQuery("SELECT * FROM TERMIN WHERE TERMINID = '" + TerminId + "';");
			Termin termin = ResultSetToTermin(rs);
			return termin;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Sucht die Termine anhand der BenutzerID
	 * @param benutzerId
	 * @return eine Liste mit den Termin Objekten
	 */
	public ArrayList<Termin> sucheTerminMitBenutzerId(int benutzerId, String datum) {
		try {
			rs = stmtSQL.executeQuery("SELECT * FROM TERMIN WHERE IDERSTELLER = '" + benutzerId + "' AND DATUM = '"+ datum +"';");
			ArrayList<Termin> list = ResultSetToTermine(rs);
			return list;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Erstellt ein Termin Objekt aus einem Result Set, Defaultwerte sind -1
	 * @param a = das übergeben Result Set
	 * @return ein Termin Objekt
	 */
	public Termin ResultSetToTermin(ResultSet a) {
		Termin termin = failedTermin; 
		try {
			termin.setTerminId(a.getInt("TERMINID"));
		} catch (SQLException e) {}
		try {
			termin.setTitel(a.getString("TITEL"));
		} catch (SQLException e) {}
		try {
			termin.setDatum(a.getString("DATUM"));
		} catch (SQLException e) {}
		try {
			termin.setDauer(a.getInt("DAUER"));
		} catch (SQLException e) {}
		try {
			termin.setIdErsteller(a.getInt("IDERSTELLER"));
		} catch (SQLException e) {}
		try {
			termin.setBenutzerEingeladen(a.getString("BENUTZEREINGELADEN"));
		} catch (SQLException e) {}
		return termin;
	}	
	
	/**
	 * Erstellt aus einem Result Set eine Liste mit Termin Objekt, Defaultwerte sind -1
	 * @param a = das übergebene Result Set
	 * @return eine Liste mit den Termin Objekten
	 */
	public ArrayList<Termin> ResultSetToTermine(ResultSet a) {
		ArrayList<Termin> list = new ArrayList<Termin>();
		try {
			while(a.next()) {
				Termin termin = failedTermin;
				list.add(termin);
				try {
					termin.setTerminId(a.getInt("TERMINID"));
				} catch (SQLException e) {}
				try {
					termin.setTitel(a.getString("TITEL"));
				} catch (SQLException e) {}
				try {
					termin.setDatum(a.getString("DATUM"));
				} catch (SQLException e) {}
				try {
					termin.setDauer(a.getInt("DAUER"));
				} catch (SQLException e) {}
				try {
					termin.setIdErsteller(a.getInt("IDERSTELLER"));
				} catch (SQLException e) {}
				try {
					termin.setBenutzerEingeladen(a.getString("BENUTZEREINGELADEN"));
				} catch (SQLException e) {}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	/**
	 * Ändert ein Termin Objekt in der Tabelle TERMIN ab indem er ein neues termin Objekt mit derselben TerminId erstellt, die Datenbank ersetzt dann das alte Objekt
	 * @param termin
	 */
	public void aendereTermin(Termin termin) {
			
			String sql = "INSERT INTO TERMIN (TERMINID, TITEL, DATUM, DAUER, IDERSTELLER) "+ 
					"VALUES("+ termin.getTerminId() +",'"+ termin.getTitel() + "','" + termin.getDatum() + "','"+ 
					termin.getDauer() +"','"+ termin.getIdErsteller() + ");";
			try {
				stmtSQL.executeUpdate(sql);
			} catch(SQLException err) {
				System.err.println(err);
			}
	}

	public void loescheTermin(int terminId) {
		try {
			stmtSQL.executeUpdate("DELETE FROM TERMIN WHERE TERMINID = '" + terminId + "';");
		} catch(SQLException err) {
			System.err.println(err);
		}
	}

	/**
	 * Online Funktionen
	 */
	
	/**
	 * Erstellt einen Eintrag in die ONLINE Tabelle
	 * @param id
	 */
	public void erstelleOnlineEintrag(int benutzerId) {
		int zeit = aktuelleZeit();
		String sql = "INSERT INTO ONLINE (BENUTZERID, ZEIT) "+ 
				"VALUES("+ benutzerId + "," + zeit + ");";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Aktualisiert einen Eintrag in der ONLINE Tabelle
	 * @param id
	 */
	public void aktualisiereOnlineEintrag(int benutzerId) {
		int zeit = aktuelleZeit();
		String sql = "UPDATE Online SET DATUM = " + zeit + " WHERE BENUTZERID = " + benutzerId + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
     * Gibt alle Einträge aus der Tabelle ONLINE zurück, entfernt vorher alle Einträge aus Online die älter als 30 min. sind
     * @return eine ArrayList mit Benutzernamen
     */
    public ArrayList<String> abfrageOnlineBenutzerName() {
        try {
        	int zeit = aktuelleZeit();     	
    		ResultSet a = prueffeOnlineEintraege();
    		if(zeit > 031 & zeit < 2359) {
    			ArrayList<Integer> list = new ArrayList<Integer>();
    			while(a.next()) {
    				list.add(a.getInt(1));
    				list.add(a.getInt(2));
    			}
    			for(int i=1; i<list.size(); i=i+2) {
    				if(Math.abs(zeit - list.get(i)) > 30) {
    					loescheOnlineEintrag(list.get((i-1)));
    				}
    			}
    		}
            rs = stmtSQL.executeQuery("SELECT BENUTZERNAME FROM ONLINE NATURAL JOIN BENUTZER;");
            ArrayList<String> benutzernamelist = new ArrayList<String>();
            while(rs.next()) {
                benutzernamelist.add(rs.getString(1));
            }
            return benutzernamelist;
        }
        catch(SQLException err) {
            System.err.println(err);
            return null;
        }
    }
    
    public ResultSet prueffeOnlineEintraege() {
    	try {
            rs = stmtSQL.executeQuery("SELECT * FROM ONLINE;");
            return rs;
        } catch(SQLException err) {
            System.err.println(err);
            return null;
        }
	}
    
	/**
	 * Lösch einen Eintrag anhand der BenutzerId
	 * @param benutzerId
	 */
	public void loescheOnlineEintrag(int benutzerId) {
		try {
			stmtSQL.executeUpdate("DELETE FROM ONLINE WHERE BENUTZERID = '" + benutzerId + "';");
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Gibt die aktuelle Systemzeit im Format hhmm zurück
	 * @return die Systemzeit
	 */
	public int aktuelleZeit() {
		Date dt = new Date();
		SimpleDateFormat aktuelleZeit = new SimpleDateFormat("hhmm");
		String zeit = (aktuelleZeit.format(dt));
		return Integer.valueOf(zeit);
	}
	
	/**
	 * Eingeladenen Funktionen
	 */
	
	/**
	 * Erstellt einen Eintrag in die Tabelle EINGELADEN
	 * @param benutzerId
	 * @param terminId
	 * @param info
	 */
	public void erstelleEintragEingeladen(int benutzerId, int terminId, String info) {
		Benutzer benutzer = sucheBenutzerMitBenutzerId(benutzerId);
		Termin termin = sucheTerminMitTerminId(terminId);
		try {
			if(termin.getTerminId() != 0 && benutzer.getBenutzerId() != 0) {
				String sql = "INSERT INTO Eingeladen (BENUTZERID, TERMINID, Info) "+ 
						"VALUES( "+ benutzer.getBenutzerId() + " , " + termin.getTerminId() + " , '"+ 
						info + "');";
				stmtSQL.executeUpdate(sql);
			}
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Ändert eine Eintrag in der Tabelle EINGELADEN
	 * @param benutzerId
	 * @param terminId
	 * @param info
	 * @param neuBenutzerId
	 * @param neuTerminId
	 */
	public void aendernEintragAnhandBenutzerIdTerminId(int benutzerId, int terminId, String info, int neuBenutzerId, int neuTerminId)  {
		
		String sql = "UPDATE Eingeladen SET BENUTZERID = " + benutzerId + ", TERMINID = " + terminId + ", INFO = '" + info + "' WHERE BENUTZERID = " + neuBenutzerId + " and TERMINID = " + neuTerminId + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Löscht einen Eintrag aus der Tabelle EINGELADEN
	 * @param benutzerId
	 * @param terminId
	 */
	public void loescheEintragEingeladenAnhandBenutzerIdTerminId(int benutzerId, int terminId) {
		
		String sql = "DELETE FROM Eingeladen WHERE BENUTZERID = " + benutzerId + " and TERMINID = " + terminId + ";";
		try {
			stmtSQL.executeUpdate(sql);
		} catch(SQLException err) {
			System.err.println(err);
		}
	}
	
	/**
	 * Gibt die Termin IDs der Einträge der Tabelle EINGELADEN anhand der BenutzerId aus
	 * @param benutzerId
	 * @return ein Result Set
	 */
	public ResultSet ausgabenEintragAnhandBenutzerId(int benutzerId) {
		
		String sql = "SELECT TERMINID FROM EINGELADEN WHERE BENUTZERID = " + benutzerId + ";";
		try {
			rs = stmtSQL.executeQuery(sql);
			return rs;
		} catch(SQLException err) {
			System.err.println(err);
			return null;
		}
	}
	
	/**
	 * Gibt alle Einträge aus der Tabelle EINGELADEN
	 * @return
	 */
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
	
	/**
    
	Sucht die Termine anhand der BenutzerID
	@param benutzerId
	@return eine Liste mit den Termin Objekten*/
	public ArrayList<Termin> sucheAlleTermine() {
	    try {
	        rs = stmtSQL.executeQuery("SELECT * FROM TERMIN;");
	        ArrayList<Termin> list = ResultSetToTermine(rs);
	        return list;
	    } catch(SQLException err) {
	        System.err.println(err);
	        return null;
	    }
    }
	
	/**
	 * Gibt eine Liste mit den Terminen zu der die übergebene BenutzerId eingeladen ist zurück
	 * @param benutzerId
	 * @return Liste mit Termin Objekten
	 */
	public ArrayList<Termin> abfrageEingeladenetermine(int benutzerId) {
		rs = ausgabenEintragAnhandBenutzerId(benutzerId);
		ArrayList<Integer> listInt = new ArrayList<Integer>();
		ArrayList<Termin> listTermin = new ArrayList<Termin>();
		try {
			while(rs.next()) {
				listInt.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
			return null;
		}
		for(int i = 0; i<listInt.size();i++) {
			listTermin.add(sucheTerminMitTerminId(listInt.get(i)));
		}
		return listTermin;
	}
	
	/**
	Authentifiziert einen Benutzer anhand seines Benutzernamen und des Passwortes
	@param benutzerName
	@param passwort
	@return ein Benutzer Objekt mit allen Attributen
	*/
	public Benutzer benutzerAuthentifkationAlleAttribute(String benutzerName, String passwort) {
	    try {
	    	Benutzer benutzer = failedBenutzer;
	    	rs = stmtSQL.executeQuery("SELECT BENUTZERNAME, PASSWORT FROM BENUTZER WHERE BENUTZERNAME = '" + benutzerName + "' AND PASSWORT = '" + passwort + "';");
	    	System.out.println("Ich bin hier");
		    rs = stmtSQL.executeQuery("SELECT BENUTZERID, BENUTZERNAME, PASSWORT, NAME, VORNAME, ISADMIN FROM BENUTZER WHERE BENUTZERNAME = '" + benutzerName + "' AND PASSWORT = '" + passwort + "';");
		    benutzer = ResultSetToBenutzer(rs);
	        return benutzer;
	    } catch(SQLException err) {
	        System.err.println(err);
	        return null;
	    }
	}
}