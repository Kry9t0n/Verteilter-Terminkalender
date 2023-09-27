package vs.terminkalender.test;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Einfache Testfunktion um den Status des Servers zu testen
 */

@Path("/hallo")
public class Test_Hello {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String hallo() {
		return "{\"name\":\"greeting\", \"message\":\"Hallo, ich funktioniere!!!\"}";
	}
}
