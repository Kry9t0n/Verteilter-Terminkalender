package client.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import client.Benutzer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class LoginClient {
	private final String TARGET_URL = "http://localhost:8080/resttest/webapi/benutzer"; // TODO: echte URL einfügen!!!
	private final String ONLINE_URL = " "; //TODO: echte URL für OnlineStatus

	private Benutzer loginBenutzer;
	private ObjectNode auth;
	private Client client;
	private boolean online = true;

	public LoginClient(String username, String passwd, Benutzer masterUser) {
		this.loginBenutzer = masterUser;
		this.loginBenutzer.setBenutzerName(username);
		this.loginBenutzer.setPasswort(passwd);

		client = ClientBuilder.newClient();
	}

	/**
	 * Nimmt das Benutzerobjekt, auf das die Referenzvariable loginBenutzer verweißt und sendet als serialisierte JSON-Daten
	 * an den Server. Zuvor wurden im Konstruktor die Attrubute Benutzername und Passwort gesetzt. 
	 * 
	 * @return Gibt ein Response Objekt zurück, das die Antwort des Servers enthält
	 */
	private Response postLoginCredentials() {
		return client.target(TARGET_URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
	}

	/**
 	* Beim Login soll der Client sich beim Server als Online melden (aufruf der Methode bei Login), dadurch
 	* wird der Client automatisch in die Table geschrieben mit allen online Clients
 	* -> zum Einloggen ein Post-Request an das Verzeichnis
 	*/
	private Response postOnlineStatus(){
		return client.target(ONLINE_URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
	}




	/**
	 * Überprüft, ob der Loginvorgang erfolgreich war. Falls der Login fehlerhaft war, wird vom Server ein Benutzerobjekt 
	 * zurückgegeben, das alle Attribute auf -1 (bei int) oder "-1" (bei String) gesetzt hat.
	 * 
	 * @param user Benutzerobjekt das überprüft werden soll.
	 * @return false falls alle Attribute auf -1 (int) oder "-1" (String) gesetzt sind, sonst true 
	 */
	private boolean loginErfolgreich(Benutzer user) { // alle Attribute gleich -1 => login fehlgeschlagen
		boolean loginErfolgreich = false;

		if (user.getBenutzerId() == -1 && user.getBenutzerName().equals("-1") && user.getPasswort().equals("-1")
				&& user.getName().equals("-1") && user.getVorname().equals("-1") && user.getIsAdmin() == -1) {
			return loginErfolgreich; // login fehlgeschlagen => loginErfolgreich = false
		} else {
			loginErfolgreich = true;
		}
		return loginErfolgreich;

	}
	
	/**
	 * Von außen zugängliche Methode, die den gesamten Ablauf des LoginClients steuert. 
	 * Führt den Login sowie Onlinestatus POST-Request durch und wertet die Serverrückgabe bei 
	 * erfolgreichen Login aus. Dabei werden die Attributwerte des vom Server zurückgeschickten 
	 * Benutzerobjektes übernommen. 
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	public void login() throws JsonMappingException, JsonProcessingException, Exception {
		Response serverResponse = postLoginCredentials();
		
		
		Benutzer benutzerObjectFromServerResponse = new ObjectMapper()
				.readValue(serverResponse.readEntity(String.class), Benutzer.class);
		// JsonNode node = new
		// ObjectMapper().readTree(serverResponse.readEntity(String.class));
		if (!loginErfolgreich(benutzerObjectFromServerResponse)) {
			throw new Exception(
					"Login war nicht erfolgreich! Kombination aus Username und Passwort stimmt nicht überein!");
		} else {
			this.loginBenutzer.setBenutzerId(benutzerObjectFromServerResponse.getBenutzerId());
			this.loginBenutzer.setName(benutzerObjectFromServerResponse.getName());
			this.loginBenutzer.setVorname(benutzerObjectFromServerResponse.getVorname());
			this.loginBenutzer.setIsAdmin(benutzerObjectFromServerResponse.getIsAdmin());
		}
	}

	/**
	 * OnlineStatus wird überprüft und die entsprechenden Exceptions geworfen
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	public void online() throws JsonMappingException, JsonProcessingException, Exception {
		Response onlineStatus = postOnlineStatus(); //integer Value (>0 oder -1)
		JsonNode node = new ObjectMapper().readTree(onlineStatus.readEntity(String.class));
		if(node.get(ONLINE_STAT).asInt() < 0){
			throw new Exception(
				"Sie sind offline!"
			);
		}else if(node.get(ONLINE_STAT).asInt() == -1){
			System.out.println("Sie sind online!");
		}else{
			throw new Exception(
				"Error!"
			);
		}

	}

	public boolean getOnline(){
        return online;
    }

	
	//Nur zum Testen!!!!
	/*
	public static void main(String[] args) {

		Benutzer masterUser = new Benutzer();
		
		System.out.println("MasterUser zuerst: " + masterUser);
		
		LoginClient l = new LoginClient("Alejandro", "k12d64fai45lf", masterUser);
		try {
			l.login();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("MasterUser danach: " + masterUser);
	}*/

}
