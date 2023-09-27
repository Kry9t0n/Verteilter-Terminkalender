package client.Client;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import client.Benutzer;
import client.Online_Rest;
import client.ServerResourceBaseURL;
import client.Termin;
import client.TerminRessoucen;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * @author Alejandro Freyermuth
 * Klasse implementiert den normalen Benutzerclient. Dieser wird automatisch von
 * der MasterController-Klasse gestartet, wenn der Login erfolgreich war und das
 * boolean isAdmin auf false gesetzt ist.
 *
 */
public class BenutzerClient {
	//Konstanten
	private final int ANZAHL_TERMIN_DARSTELLUNG = 7; // d.h. 7 Tage
	private final String TERMIN_BASE_URL = "http://localhost:8080/VS_Server/webapi/termin/"; // TODO
	
	//Attribute
	private Benutzer benutzer;
	private ArrayList<ArrayList<Termin>> terminSpeicher; // ArrayList die die ArrayListen der einzelnen Tage speichert
	private Client client;
	private ArrayList<Termin> eingeladen;
	private PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType("java.util").build();
	/**
	 * Konstruktor erstellt einen neuen Benutzerclient, dabei werden automatisch
	 * alle Termine im Darstellungszeitraum gefetcht. 
	 * @param masterUser Clientseitiges Benutzerobjekt, das vom Mastercontroller erstellt und verwaltet wird
	 */
	public BenutzerClient(Benutzer masterUser) {
		eingeladen = new ArrayList<>();
		terminSpeicher = new ArrayList<>();
		client = ClientBuilder.newClient();
		this.benutzer = masterUser;
		fetchTermineInDarstellungszeitraum();
	}
	
	public void fetchTermineInDarstellungszeitraum() {
		LocalDate tag = null;
		for (int i = 0; i < ANZAHL_TERMIN_DARSTELLUNG; i++) {
			if (i == 0) {
				tag = LocalDate.now();
			} else {
				tag = tag.plusDays(1);
			}
			//ArrayList<Termin> tagesListe = fetchAlleTermineEinesTages(tag);
			ArrayList<Termin> tagesListe = TerminRessoucen.getAlleTermineAnEinemTag(client, tag, benutzer);
			//überprüfen und ggf. einfügen
			if(tagesListe != null) {
				terminSpeicher.add(i, tagesListe);
				
			}else {
				System.err.println("Fehler!!! Tagesliste ist null!"); //TODO: nur zum testen!!!
			}
		}
	}
	
	
	public void getEinladungen() {
		Response response = client.target(ServerResourceBaseURL.EINGELADEN_BASE_URL.getURL() + "/benutzer/" + benutzer.getBenutzerId())
									.request(MediaType.APPLICATION_JSON)
									.get();
		try {
			eingeladen = new ObjectMapper().setPolymorphicTypeValidator(ptv).readValue(response.readEntity(String.class), new TypeReference<ArrayList<Termin>>() {});
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Benutzer> fetchBenutzerOnlineListe() throws JsonMappingException, JsonProcessingException {
		return Online_Rest.getAlleBenutzerDieOnlineSind(client);
	}
	
	/**
	 * Alle Termine eines Benutzers an einem Tag vom Server fetchen. 
	 * @param tag als LocalDate Objekt
	 * @return ArrayListe mit allen Terminen des angegebenen Datums
	 */
	/* --- Legacy
	public ArrayList<Termin> fetchAlleTermineEinesTages(LocalDate tag) {
		ArrayList<Termin> tagesListe = null;
		final String FETCH_URL = TERMIN_BASE_URL + benutzer.getBenutzerId() + "/" +tag.getDayOfMonth() + "," +  tag.getMonthValue() + "," + tag.getYear();
		
		//TEST
		//System.out.println("Ich fetche Tag: " + tag + "unter der URL: " + FETCH_URL); //TODO: vor Abgabe entfernen
		
		// GET-Request an Server senden
		Response serverRes = client
							 .target(FETCH_URL)
							 .request(MediaType.APPLICATION_JSON)
							 .get();
		
		try {
			tagesListe = new ObjectMapper().readValue(serverRes.readEntity(String.class), ArrayList.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return tagesListe;

	}*/

	public ArrayList<ArrayList<Termin>> getTerminSpeicher() {
		return terminSpeicher;
	}

	public Client getClient() {
		return client;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public int getANZAHL_TERMIN_DARSTELLUNG() {
		return ANZAHL_TERMIN_DARSTELLUNG;
	}

	public ArrayList<Termin> getEingeladen() {
		return eingeladen;
	}
	
	
	
}
