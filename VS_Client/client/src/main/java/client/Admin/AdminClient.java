package client.Admin;

import client.Benutzer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

public class AdminClient {
	private Benutzer benutzer;
	private Client client;
	
	public AdminClient(Benutzer benutzer) {
		this.benutzer = benutzer;
		this.client = ClientBuilder.newClient();
	}
	
	//TODO: Admin Client Funktionen
}
