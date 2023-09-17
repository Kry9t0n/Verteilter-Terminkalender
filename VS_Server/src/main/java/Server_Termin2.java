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
public class Server_Termin2
{
	@POST
    @Path("/erstellen")
    @Consumes(MediaType.APPLICATION_JSON)
    public void erstellen(Termin termindaten) 
	{
		DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		
		db.oeffneDB();

		db.erstelleTerminUndEintragEingeladenAnhandBenutzername(termindaten);

		db.schliesseDB();
	}
	
	@POST
    @Path("/loeschen/{terminId}/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void loeschen(@PathParam("TerminId") int terminId, @PathParam("benutzerId") int benutzerId) 
	{
		DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		
		db.oeffneDB();

		db.loescheEintragEingeladenAnhandBenutzerIdTerminId(benutzerId, terminId);

		db.schliesseDB();
	}
	
	@GET
    @Path("/abfragen/{benutzerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Termin abfragen(@PathParam("BenutzerId") int id) 
	{
		Termin termin = null;
		
		DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		
		db.oeffneDB();

		ResultSet rs = db.ausgabenEintragAnhandBenutzerId(id);

		db.schliesseDB();
		
		return termin;
	}
}