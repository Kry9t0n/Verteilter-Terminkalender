package client.Admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Benutzer;
import client.Monitoring_Data;
import client.ServerResourceBaseURL;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
	
	public Monitoring_Data fetcheServerStatistiken() throws JsonMappingException, JsonProcessingException {
		Response res = client
						.target(ServerResourceBaseURL.EINGELADEN_BASE_URL.getURL())
						.request(MediaType.APPLICATION_JSON)
						.get();
		
		Monitoring_Data statData = new ObjectMapper().readValue(res.readEntity(String.class), Monitoring_Data.class);
		return statData;
	}


}
