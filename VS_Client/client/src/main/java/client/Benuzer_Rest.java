package client;



import java.util.ArrayList;

import org.apache.http.client.HttpResponseException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
public class Benuzer_Rest {
	
	private static final int STATUS_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final int STATUS_NO_CONTENT = 204;

	//private static final String BASE_URL = "http://localhost:8080/VS_Server/webapi/benutzer";
	
	private static PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType("java.util").build();
	
	//POST
	public static Response addBenutzer(Client client, Benutzer benuzer) {
		try {
			Response response = client.target(ServerResourceBaseURL.BENUTZER_BASE_URL.getURL())
					.request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(benuzer, MediaType.APPLICATION_JSON));
			
			System.out.println(response.readEntity(String.class));
			
			if (response.getStatus() == STATUS_OK) {
				
				System.out.println("That was successful!");
				return response;
			} else {
				throw new HttpResponseException(response.getStatus(), response.getStatusInfo().getReasonPhrase());
				//throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//GET ALL
	public static ArrayList<Benutzer> getAllBenutzer(Client client) {
		try {
			Response response = client.target(ServerResourceBaseURL.BENUTZER_BASE_URL.getURL())
					.request(MediaType.APPLICATION_JSON)
					.get();

			if (response.getStatus() == STATUS_OK) {

				ArrayList<Benutzer> benutzerListe = new ObjectMapper().setPolymorphicTypeValidator(ptv).readValue(response.readEntity(String.class), new TypeReference<ArrayList<Benutzer>>() {}); //TODO
				return benutzerListe;
			} else {
				throw new HttpResponseException(response.getStatus(), "Fehler beim Abrufen aller Benutzer: " + response.getStatusInfo().getReasonPhrase());
				//throw new RuntimeException("Fehler beim Abrufen aller Benutzer: " + response.getStatusInfo().getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//GET ONE
	public static Benutzer abfragenEinesBenutzers(Client client, int benutzerId) {
		try {
			Response response = client.target(ServerResourceBaseURL.BENUTZER_BASE_URL.getURL()).path("/" + String.valueOf(benutzerId))
					.request(MediaType.APPLICATION_JSON)
					.get();

			if (response.getStatus() == STATUS_OK) {
				
				Benutzer benutzer = new ObjectMapper().readValue(response.readEntity(String.class), Benutzer.class);
				return benutzer;
			} else {
				throw new HttpResponseException(response.getStatus(), "Fehler beim Abrufen des Benutzers: " + response.getStatusInfo().getReasonPhrase());
				//throw new RuntimeException("Fehler beim Abrufen des Benutzers: " + response.getStatusInfo().getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//DELETE
	public static Response removeBenutzer(Client client, int benutzerId) {
		try {
			Response response = client.target(ServerResourceBaseURL.BENUTZER_BASE_URL.getURL()).path("/" + String.valueOf(benutzerId))
					.request()
					.delete();

			if (response.getStatus() == STATUS_OK) {
				System.out.println("That was successful!");
				return response;
			} else {
				throw new HttpResponseException(response.getStatus(), response.getStatusInfo().getReasonPhrase());
				//throw new RuntimeException(response.getStatusInfo().getReasonPhrase());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}




}
