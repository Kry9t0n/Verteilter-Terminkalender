package test;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import client.LoginClient;
import jakarta.ws.rs.core.Response;

public class LoginClientTest {
	private static final int PORT = 8080;
	private WireMockServer server;
	
	@Before
	public void setup() {
		server = new WireMockServer(PORT);
		server.start();
		configureFor("localhost", PORT);
	}
	
	@After
	public void shutdown() {
		server.stop();
	}
	
	@Test
	public void testeLoginClient_login_mit_username_und_passwort() {
		final int STATUS_OK = 200;
		stubFor(post(urlEqualTo("/login")) 
		        .willReturn(aResponse()
		            .withStatus(200)
		            .withHeader("Content-Type", "application/json")
		            .withBody("{\"isAdmin\": false, \"loginErfolgreich\": 1, \"BenutzerID\": 1123}"))); 
		
		
		LoginClient client = new LoginClient("User1", "A#d54l!öps");
		Response res = client.postLoginCredentials();
		
		assertEquals(res.getStatus(), STATUS_OK);
		assertFalse(client.isAdmin(res));
		
	}
}
