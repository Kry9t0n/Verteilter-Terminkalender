package client;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class Online_Rest {
	private static PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
			.allowIfSubType("java.util").build();
	
	/**
	 * GET-Request an online Ressource, die die Liste aller Benutzer die online sind abfrägt
	 * @param client
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static ArrayList<Benutzer> getAlleBenutzerDieOnlineSind(Client client) throws JsonMappingException, JsonProcessingException{
		Response res = client
						.target(ServerResourceBaseURL.ONLINE_BASE_URL.getURL())
						.request(MediaType.APPLICATION_JSON)
						.get();
		return new ObjectMapper().setPolymorphicTypeValidator(ptv).readValue(res.readEntity(String.class), new TypeReference<ArrayList<Benutzer>>() {});
	}
	
}