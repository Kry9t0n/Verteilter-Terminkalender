package restclient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TestClient {
	private static final String URL = "http://localhost:8080/resttest/webapi/users";
	private Client c;
	
	public TestClient() {
		c = ClientBuilder.newClient();
	}
	
	public Response postUserCredentials(User u1) {
		return c
				.target(URL)
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(u1, MediaType.APPLICATION_JSON));
	}
	
	
	public static void main(String[] args) {
		User u_test = new User();
		u_test.setUsername("Alejandro");
		u_test.setPassword("kdfajdf");
		u_test.setAdmin(false);
		
		TestClient tc = new TestClient();
		Response res = tc.postUserCredentials(u_test);
		
		
		
		System.out.println(String.format("Serverstatus: %d \n", res.getStatus()));
		
		System.out.println(res.readEntity(String.class));
		
	}
	
}
