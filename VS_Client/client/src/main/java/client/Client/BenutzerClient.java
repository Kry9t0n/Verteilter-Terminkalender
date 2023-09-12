package client.Client;

import java.time.LocalDate;
import java.util.ArrayList;

import client.Benutzer;
import client.Termin;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Klasse implementiert den normalen Benutzerclient. Dieser wird automatisch von
 * der MasterController-Klasse gestartet, wenn der Login erfolgreich war und das
 * boolean isAdmin auf false gesetzt ist.
 *
 */
public class BenutzerClient {
	private final int ANZAHL_TERMIN_DARSTELLUNG = 7; // d.h. 7 Tage
	private final String TERMIN_BASE_URL = "http://localhost:8080/resttest/webapi/1234/Termine/"; // TODO

	private Benutzer benutzer;
	private ArrayList<ArrayList<Termin>> terminSpeicher; // ArrayList die die ArrayListen der einzelnen Tage speichert
	private Client client;

	public BenutzerClient(Benutzer masterUser) {
		terminSpeicher = new ArrayList<>();
		client = ClientBuilder.newClient();
		this.benutzer = masterUser;
		setupTerminSpeicher();
		fetchTermineInDarstellungszeitraum();
	}

	private void setupTerminSpeicher() { // befüllt den terminSpeicher mit gleich vielen ArrayListen wie Tage im
											// Darstellungszeitraum sind
		for (int i = 0; i < ANZAHL_TERMIN_DARSTELLUNG; i++) {
			terminSpeicher.add(new ArrayList<Termin>());
		}
	}

	private void fetchTermineInDarstellungszeitraum() {
		for (int i = 0; i < ANZAHL_TERMIN_DARSTELLUNG; i++) {
			fetchAlleTermineEinesTages(i);
		}
	}

	private void fetchAlleTermineEinesTages(int tagesIndex) {
		ArrayList<Termin> tagesArrayList = terminSpeicher.get(tagesIndex);
		LocalDate tag = null;
		

		if (tagesIndex == 0) {
			tag = LocalDate.now();
		} else {
			tag = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
					LocalDate.now().getDayOfMonth() + tagesIndex);
		}
		
		String FETCH_URL = TERMIN_BASE_URL + tag.toString();
		
		//TEST
		System.out.println("Ich fetche Tag: " + tag + "unter der URL: " + FETCH_URL);
		
		
		
		// GET-Request an Server senden
		
		//Response res = client.target(TERMIN_URL).request(MediaType.APPLICATION_JSON).get(Entity.entity(tag, MediaType.APPLICATION_JSON));

	}
}
