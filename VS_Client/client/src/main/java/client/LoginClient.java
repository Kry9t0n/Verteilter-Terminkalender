package client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONObject;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class LoginClient {
	private final String SERVER_URL = ""; //TODO
	private final String LOGIN_URL = ""; //TODO
	
	private String username;
	private String passwd;
	private JSONObject auth;
	private Client client;
	private WebTarget postTarget;
	
	public LoginClient(String username, String passwd) {
		this.username = username;
		this.passwd = passwd;
		postTarget = client.target(SERVER_URL).path(LOGIN_URL);
		
		auth = new JSONObject().put("Username", this.username).put("Password", this.passwd);
		
		client = JerseyClientBuilder.newClient();
	}
	
	
	/**
	 * Sendet Benutzername und Passwort als JSON-Daten per POST-Request an den Server.
	 * @return Gibt ein Response Objekt zurück, das die Antwort des Servers enthält
	 */
	private Response postLoginCredentials() {
		Invocation.Builder invocation = postTarget.request(MediaType.APPLICATION_JSON);
		Response res = invocation.post(Entity.entity(auth, MediaType.APPLICATION_JSON));
		System.out.println(String.format("Status: %i \n Response %s \n", res.getStatus(), res.readEntity(String.class)));
		return res;
	}
	
	/**
	 * 
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls der Server das Paar <Benutzername, Passwort> in seiner DB gefunden hat, sonst false
	 */
	private boolean loginErfolgreich(Response res) {
		//TODO
	}
	
	/**
	 * Wertet den boolean isAdmin in der Serverantwort aus.
	 * @param res Response Objekt das die Serverantwort enthält
	 * @return true falls isAdmin = true, sonst false
	 */
	private boolean isAdmin(Response res) {
		//TODO
	}
	
	
	private boolean serverStatusOk(Response res) {
		boolean isStatusOk = true;
		int serverStatus = res.getStatus();
		if(serverStatus != /*ServerStatus*/) {
			isStatusOk = false;
		}
		
		return isStatusOk;
	}
	
	public void login() {
		Response serverResponse = postLoginCredentials();
		//TODO: Onlinestatus
		 //TODO: Status testen
	}
	
	
	
	
	
	
}
