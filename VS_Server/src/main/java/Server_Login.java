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


@Path("/login")
public class Server_Login 
{
	@POST
    @Path("/benutzer")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON) //??
    public Response anmelden(Benutzer benutzerdaten) 
	{	
        return Response.ok(checkAuthentication(benutzerdaten), MediaType.APPLICATION_JSON).build();
    }

    private Benutzer checkAuthentication(Benutzer benutzerdaten) 
    {
		Benutzer benutzerdatenTemp = null;

    	DB_Funktionen db = new DB_Funktionen("SA","");
		db.oeffneDB();

	    benutzerdatenTemp = db.benutzerAuthentifkationAlleAttribute(benutzerdaten.getBenutzerName(), benutzerdaten.getPasswort());
	    
		if (benutzerdatenTemp == null)
		{
			benutzerdatenTemp = new Benutzer(-1, "-1", "-1", "-1","-1", -1);
		}
		else
		{
			//Wenn Einloggen erfolgreich, wird Online Eintrag erstellt
			db.erstelleOnlineEintrag(benutzerdatenTemp.getBenutzerId());
		}
		
		db.schliesseDB();

		return benutzerdatenTemp;
    }
}