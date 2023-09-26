package client;

import java.util.ArrayList;

import org.apache.http.client.HttpResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class Einladung_Rest {

	private static final int STATUS_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final int STATUS_NO_CONTENT = 204;
	
	private static final String BASE_URL = "http://localhost:8080/VS_Server/webapi/eingeladen"; 
	
	private static PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType("java.util").build();
	
	//GET Einladung eines Benutzers
	public static ArrayList<Termin> getEinladungen(Client client, int benutzerId) {
		try {
			Response response = client.target(BASE_URL+"/benutzer/"+ String.valueOf(benutzerId))
									.request(MediaType.APPLICATION_JSON)
									.get();
			
			if (response.getStatus() == STATUS_OK) {
				ArrayList<Termin> einladeListe = new ObjectMapper().setPolymorphicTypeValidator(ptv).readValue(response.readEntity(String.class), new TypeReference<ArrayList<Termin>>() {});
				return einladeListe;
			} else {
				// Fehlermeldung ausgeben, wenn die Anfrage nicht erfolgreich war
				throw new HttpResponseException(response.getStatus(), response.getStatusInfo().getReasonPhrase());
				//throw new RuntimeException("Fehler beim Abrufen Einladungen: " + response.getStatusInfo().getReasonPhrase());
			}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
		}
	
	//DELETE
	public static Response removeEinladung(Client client, int terminId, int benutzerId) {
		try {
			Response response = client.target(BASE_URL).path("/" + String.valueOf(terminId)+"/"+String.valueOf(benutzerId))
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