package client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONObject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;


public class LoginClient {
	private final String URL = ""; //TODO
	
	private String username;
	private String passwd;
	private JSONObject auth;
	private Client client;
	
	public LoginClient(String username, String passwd) {
		this.username = username;
		this.passwd = passwd;
		
		auth = new JSONObject();
		auth.put("Username", this.username);
		auth.put("Password", this.passwd);
		
		client = JerseyClientBuilder.newClient();
	}
	
	public Response login() {
		Response res = client.target(URL).request().post(Entity.xml(auth));
		return res;
	}
	
	//TODO: Response des Servers auswerten
	
}
