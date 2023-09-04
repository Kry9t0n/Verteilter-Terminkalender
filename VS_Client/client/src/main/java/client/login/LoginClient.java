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
	private final String TARGET_URL = "http:/localhost:8080/resttest/webapi/users"; // TODO: echte URL einfügen!!!

	private Benutzer loginBenutzer;
	private ObjectNode auth;
	private Client client;

	public LoginClient(String username, String passwd) {
		this.loginBenutzer = new Benutzer(passwd, username);

		client = ClientBuilder.newClient();
	}

	/**
	 * Sendet Benutzername und Passwort als JSON-Daten per POST-Request an den
	 * Server.
	 * 
	 * @return Gibt ein Response Objekt zurück, das die Antwort des Servers enthält
	 */
	public Response postLoginCredentials() {
		return client.target(TARGET_URL).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
	}

	/**
	 * 
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls der Server das Paar <Benutzername, Passwort> in seiner DB
	 *         gefunden hat, sonst false
	 */
	private boolean loginErfolgreich(JsonNode node) { // alle Attribute gleich -1 => login fehlgeschlagen
		boolean loginErfolgreich = false;

		if (node.get("benutzerId").asInt() == -1 && node.get("benutzerName").asText().equals("-1")
				&& node.get("passwort").asText().equals("-1") && node.get("name").asText().equals("-1")
				&& node.get("vorname").asText().equals("-1") && node.get("isAdmin").asInt() == -1) {
			return loginErfolgreich; // login fehlgeschlagen => loginErfolgreich = false
		} else {
			loginErfolgreich = true;
		}
		return loginErfolgreich;

	}

	/**
	 * Wertet den boolean isAdmin in der Serverantwort aus.
	 * 
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls isAdmin = true, sonst false
	 */
	private boolean isAdmin(JsonNode node) {
		// ist isAdmin in der Antwort des Servers auf 1 gesetzt bedeutet das, dass der
		// user admin rechte hat
		return node.get("isAdmin").asInt() == 1;
	}

	public void login() throws JsonMappingException, JsonProcessingException, Exception {
		Response serverResponse = postLoginCredentials();
		// TODO: Onlinestatus
		JsonNode node = new ObjectMapper().readTree(serverResponse.readEntity(String.class));
		if(!loginErfolgreich(node)) {
			throw new Exception("Login war nicht erfolgreich! Kombination aus Username und Passwort stimmt nicht überein!");
		}
	}

	public static void main(String[] args) {
		LoginClient l = new LoginClient("Alejandro", "k12d64fai45lf");
		l.postLoginCredentials();
	}

}
