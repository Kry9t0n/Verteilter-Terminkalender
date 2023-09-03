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
	private final String TARGET_URL = "http:/localhost:8080/resttest/webapi/users"; //TODO: echte URL einfügen!!!
	private final int SERVER_STATUS_OK = 200;
	
	private Benutzer loginBenutzer;
	private ObjectNode auth;
	private Client client;
	
	public LoginClient(String username, String passwd) {
		this.loginBenutzer = new Benutzer(passwd, username);
		
		client = ClientBuilder.newClient();
	}
	
	
	/**
	 * Sendet Benutzername und Passwort als JSON-Daten per POST-Request an den Server.
	 * @return Gibt ein Response Objekt zurück, das die Antwort des Servers enthält
	 */
	public Response postLoginCredentials() {
		return client
				.target(TARGET_URL)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(loginBenutzer, MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls der Server das Paar <Benutzername, Passwort> in seiner DB gefunden hat, sonst false
	 */
	private boolean loginErfolgreich(Response res) {
		//TODO
		return false; //Einfach ein Wert damit Eclipse nicht mehr meckert
	}
	
	/**
	 * Wertet den boolean isAdmin in der Serverantwort aus.
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls isAdmin = true, sonst false
	 */
	public boolean isAdmin(Response res) {
		String responseBody = res.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = null;
		try {
			node = mapper.readTree(responseBody);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node.get("isAdmin").asBoolean();
	}
	
	
	private boolean serverStatusOk(Response res) {
		boolean isStatusOk = true;
		int serverStatus = res.getStatus();
		if(serverStatus != SERVER_STATUS_OK) { //Server-Code 200 = alles ok!!!
			isStatusOk = false;
		}
		
		return isStatusOk;
	}
	
	public void login() {
		Response serverResponse = postLoginCredentials();
		//TODO: Onlinestatus
		 //TODO: Status testen
	}
	
	
	
	public static void main(String[] args) {
		LoginClient l = new LoginClient("Alejandro", "k12d64fai45lf");
		l.postLoginCredentials();
	}
	
	
}
