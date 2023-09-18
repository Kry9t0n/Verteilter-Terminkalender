import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/termin")
public class Server_Termin
{
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@POST
    @Path("/erstellen")
    @Consumes(MediaType.APPLICATION_JSON)
    public void erstellen(String jsonDaten) 
	{
		Termin termindaten = jsonZuTermin(jsonDaten);
		
		DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		
		db.oeffneDB();

		db.erstelleTerminUndEintragEingeladenAnhandBenutzername(termindaten);

		db.schliesseDB();
	}
	
	@GET
    @Path("/abfragen/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Termin abfragen(@PathParam("id") int id, String jsonDaten) 
	{
		db.getTermin(id); //NUR BEISPIEL
		Termin termin = null;
		return termin;
	}
	
	private Termin jsonZuTermin(String jsonDaten)
	{
		Termin termindaten = null;
		try
		{
			termindaten = objectMapper.readValue(jsonDaten, Termin.class);
		} 
		catch (JsonMappingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (JsonProcessingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return termindaten;
	}
}