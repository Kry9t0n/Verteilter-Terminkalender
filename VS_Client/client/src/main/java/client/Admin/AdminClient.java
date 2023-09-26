package client.Admin;

import client.Benutzer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

public class AdminClient {

	private Benutzer benutzer;
	private Client client;
	
	public AdminClient(Benutzer masterUser) {
		client = ClientBuilder.newClient();
		this.benutzer = masterUser;
	}

	public Client getClient() {
		return client;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}


}
