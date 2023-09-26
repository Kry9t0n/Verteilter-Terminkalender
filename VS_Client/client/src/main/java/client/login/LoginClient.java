package client.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import client.Benutzer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;



/**
 * 
 * @author Alejandro Freyermuth, Yannik Geber
 *
 */

public class LoginClient {
	private final String TARGET_URL = "http://localhost:8080/VS_Server/webapi/login/"; 

	private Benutzer loginBenutzer;
	private Client client;

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
		System.out.println(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
		return client.target(TARGET_URL).request(MediaType.APPLICATION_JSON)
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
		
		//alt: user.getBenutzerId() == -1 && user.getBenutzerName().equals("-1") && user.getPasswort().equals("-1") && user.getName().equals("-1") && user.getVorname().equals("-1") && user.getIsAdmin() == -1
		if (user.getBenutzerId() == 0 && user.getIsAdmin() == 0) {
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

	public Benutzer getLoginBenutzer(){
		return loginBenutzer;
	}

	public Client getClient(){
		return client;
	}
	
}
	