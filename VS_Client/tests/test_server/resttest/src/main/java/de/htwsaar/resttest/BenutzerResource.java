package de.htwsaar.resttest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("benutzer")
public class BenutzerResource {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Benutzer postBenutzer(Benutzer b) {
		if(!b.getBenutzerName().isEmpty() && !b.getPasswort().isEmpty() ){
			b.setBenutzerId(1234);
			b.setIsAdmin(1);
			b.setName("Freyermuth");
			b.setVorname("Alejandro");
		}
		
		return b;
	}
	
	
}
