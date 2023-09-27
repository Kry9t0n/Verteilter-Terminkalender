package client.Admin;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import client.Benutzer;
import client.Benuzer_Rest;
import client.Monitoring_Data;
import client.ServerResourceBaseURL;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
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
		Response res = client.target(ServerResourceBaseURL.MONITORING_BASE_URL.getURL())
				.request(MediaType.APPLICATION_JSON).get();

		Monitoring_Data statData = new ObjectMapper().readValue(res.readEntity(String.class), Monitoring_Data.class);
		return statData;
	}

	public Benutzer abfrageBenutzerAnhandName(String name) {
		Benutzer benutzerGefunden = null;
		ArrayList<Benutzer> listAllUser = Benuzer_Rest.getAllBenutzer(client);
		for (Benutzer b : listAllUser) {
			if (b.getBenutzerName() == name) {
				benutzerGefunden = b;
				break;
			}
		}

		return benutzerGefunden;
	}
}
