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

	private Benutzer loginBenutzer;
	private ObjectNode auth;
	private Client client;

	public LoginClient(String username, String passwd, Benutzer masterUser) {
		this.loginBenutzer = masterUser;
		this.loginBenutzer.setBenutzerName(username);
		this.loginBenutzer.setPasswort(passwd);

		client = ClientBuilder.newClient();
	}

	/**
	 * Sendet Benutzername und Passwort als JSON-Daten per POST-Request an den
	 * Server.
	 * 
	 * @return Gibt ein Response Objekt zurück, das die Antwort des Servers enthält
	 */
	private Response postLoginCredentials() {
		return client.target(TARGET_URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
	}

	/**
	 * 
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls der Server das Paar <Benutzername, Passwort> in seiner DB
	 *         gefunden hat, sonst false
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

	public void login() throws JsonMappingException, JsonProcessingException, Exception {
		Response serverResponse = postLoginCredentials();
		// TODO: Onlinestatus
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
