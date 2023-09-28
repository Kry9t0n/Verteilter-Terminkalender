package client.Admin;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import client.Benutzer;
import client.Benuzer_Rest;
import client.Monitoring_Data;
import client.Termin;
import client.TerminRessoucen;
import client.mastercontroller.MasterController;
import jakarta.ws.rs.client.Client;

public class AdminDialog {

	private Scanner input;
	private Client client;
	private AdminClient adminClient;
	
	private static final int ENDE = 0;
	private static final int BENUTZER_ERSTELLEN = 1;
	private static final int BENUTZER_AUSGEBEN = 2;
	private static final int BENUTZER_LOESCHEN = 3;
	private static final int BENUTZER_SUCHEN = 4;
	private static final int TERMINE_AUSGEBEN = 5;
	private static final int TERMIN_LOESCHEN = 6;
	private static final int SERVER_DATEN_AUSGEBEN = 7;
	private static final int ONLINE_BENUTZER_AUSGEBEN = 8;
	
	public AdminDialog(AdminClient adminClient) {
		this.adminClient = adminClient;
		this.client = adminClient.getClient();
		input = new Scanner(System.in);
	}

	public void StartAdminDialog() {
		System.out.print("Der Dialog für den Admin wird ausgeführt\n");
		System.out.print("------------------------------------------\n");
		int eingabe = 0;
		
		do {
			try {
				DialogAusgeben();
				eingabe = eingebeLesen();
				eingabeAusfuehren(eingabe);
			} catch(IllegalArgumentException e) {
				System.out.println(e);
			} catch(InputMismatchException e) {
				System.out.println(e);
				input.nextLine();
			} catch(Exception e) {
				System.out.println(e); //hier vlt e.getMessage() ????
				e.printStackTrace(System.out);
			}
		} while(eingabe != ENDE);
		
	}

	private void eingabeAusfuehren(int eingabe) throws JsonMappingException, JsonProcessingException {
		switch(eingabe){
		case 1: 
			benutzerErstellen();
			break;
		case 2:
			benutzerAusgeben();
			break;
		case 3:
			benutzerLoeschen();
			break;
		case 4:
			benutzerSuchen();
			break;
		case 5:
			termineAusgeben();
			
			break;
		case 6:
			termineLoeschen();
			break;
		case 7:
			server_daten_ausgeben();
			break;
		case 8:
			listeOnlineBenutzerAuf();
			break;
		case 0:
			System.out.println("AdminDialog wurde beendet!");
			MasterController.programmBeenden(0);
			break;
		default:
			System.out.println("Eingabe falsch!");
		}
		
	}

	private void listeOnlineBenutzerAuf() {
		ArrayList<String> onlineListe = null;
		try {
			onlineListe = adminClient.fetchBenutzerOnlineListe();
		} catch (JsonProcessingException e) {
			System.out.println("Fehler beim fetchen der Onlineliste!");
		}
		
		if(onlineListe != null) {
			System.out.println("### Onlineliste ###");
			for(String s : onlineListe) {
				System.out.println("Benutzername: " + s);
			}
			System.out.println("------------------------------------------\n");
		}
		
	}
	
	private void benutzerErstellen() {
		String Vorname;
		String name;
		String password;
		
		System.out.println("Vorname des Benutzers:\n");
		Vorname = input.next();
		System.out.println("Name des Benutzers:\n");
		name = input.next();
		System.out.println("Password des Benutzers:\n");
		password = input.next();
		Benutzer benutzer = new Benutzer(password,Vorname,name,0);
		Benuzer_Rest.addBenutzer(client,benutzer);
		
		System.out.println("Benutzers erstellt!\n");
	}

	
	private void benutzerAusgeben() {
		
		ArrayList<Benutzer> benutzerListe = Benuzer_Rest.getAllBenutzer(client);
		
		for(Benutzer b : benutzerListe) {
			System.out.println(b);
		}
		System.out.println("------------------------------------------\n");
		
	}
	
	private void benutzerLoeschen() {
		System.out.println("Welchen Benutzer(Benutzer Id) soll geloescht werden?\n");
		int benutzerId = leseGanzzahlEingabe();
		Benuzer_Rest.removeBenutzer(client,benutzerId);
	
		System.out.println("Benutzer wuerde geloescht!\n");
	}
	
	private void benutzerSuchen() {
		System.out.println("Welcher Benutzer(Benutzername) soll gesucht werden?");
		String benutzerName = input.next();
		System.out.println("Suche Benutzer...");
		Benutzer benutzerGefunden = adminClient.abfrageBenutzerAnhandName(benutzerName);
		if(benutzerGefunden != null) {
			System.out.println("### Treffer: ###");
			System.out.println(benutzerGefunden);
		}else {
			System.out.println("Keine Treffer");
		}
		System.out.println("------------------------------------------\n");
	}
	
	private void termineAusgeben() {
		
		ArrayList<Termin> terminListe = TerminRessoucen.getAllTermine(client);
		
		for(Termin t : terminListe) {
			System.out.println(t);
		}
		System.out.println("------------------------------------------\n");
		
	}
	
	private void termineLoeschen() {
		System.out.println("Welchen Termin(Termin Id) soll geloescht werden?\n");
		int terminId = leseGanzzahlEingabe();
		
		TerminRessoucen.removeTermin(client,terminId);
		
		System.out.println("Termin wuerde geloescht!\n");
		
	}
	
	private void server_daten_ausgeben() throws JsonMappingException, JsonProcessingException {
		Monitoring_Data statData = adminClient.fetcheServerStatistiken();
		System.out.println("### Serverstatistiken ###");
		System.out.println("Anzahl der Requests        : " + statData.getRequestCount());
		System.out.println("Anzahl der Errors          : " + statData.getErrorCount());
		System.out.println("Anzahl der erhaltenen Bytes: " + statData.getBytesReceived());
		System.out.println("Anzahl der gesendeten Bytes: " + statData.getBytesSent());
		System.out.println("Laufzeit                   : " + statData.getProcessingTime());
		System.out.println("------------------------------------------\n");
	}

	private int eingebeLesen() {
	    while (!input.hasNextInt()) {
	    	throw new InputMismatchException("Nur Interger eingeben, Keine Strings!");
	        
	    }
	    int eingabe = input.nextInt();
	    return eingabe;
	}

	private void DialogAusgeben() {
		System.out.print(BENUTZER_ERSTELLEN + ":Benutzer erstellen\n"
				+ BENUTZER_AUSGEBEN + ":Benutzer ausgeben\n"
				+ BENUTZER_LOESCHEN + ":Benutzer loeschen\n"
				+ BENUTZER_SUCHEN + ":Benutzer suchen\n"
				+ TERMINE_AUSGEBEN + ":Termin ausgeben\n"
				+ TERMIN_LOESCHEN + ":Termin loeschen\n"
				+ SERVER_DATEN_AUSGEBEN + ":Server daten ausgeben\n"
				+ ONLINE_BENUTZER_AUSGEBEN + ":Gibt eine Liste aller Benutzer die online sind aus\n"
				+ ENDE + ":Ende\n");
	}
	
	private int leseGanzzahlEingabe() {
        if (input.hasNextInt()) {
            int zahl = input.nextInt();
            input.nextLine(); // Um den Zeilenumbruch zu verarbeiten
            return zahl;
        } else {
        	throw new InputMismatchException("Nur Interger eingeben, Keine Strings!");
        }
    
}	
	
	/*	
	public static void main(String[] args) {
		
		AdminDialog dialog = new AdminDialog();
		dialog.StartAdminDialog();
	}	*/
	

}


