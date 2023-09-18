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
public class Server_Login2 
{
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@POST
    @Path("/benutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response anmelden(String jsonDaten) 
	{
		Benutzer benutzerdaten = jsonZuBenutzer(jsonDaten);
		Benutzer benutzerdatenTemp = null;

		ResultSet rs = checkAuthentication(benutzerdaten);
		
        if (rs != null) 
        {
            try 
            {
            	benutzerdatenTemp = new Benutzer (rs.getInt(1), rs.getInt(2));
			} 
            catch (SQLException e) 
            {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            return Response.ok(benutzerdatenTemp, MediaType.APPLICATION_JSON).build();
        } 
        else 
        {
        	benutzerdatenTemp = new Benutzer(-1, "-1", "-1", "-1","-1", -1);
        	return Response.ok(benutzerdatenTemp, MediaType.APPLICATION_JSON).build();
        }
    }
	
	private Benutzer jsonZuBenutzer(String jsonDaten)
	{
		Benutzer benutzerdaten = null;
		try
		{
			benutzerdaten = objectMapper.readValue(jsonDaten, Benutzer.class);
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
        
        return benutzerdaten;
	}

    private ResultSet checkAuthentication(Benutzer benutzerdaten) 
    {
    	DB_Tabelle_Zugriff db = new DB_Tabelle_Zugriff("SA","");
		db.oeffneDB();

		ResultSet rs = db.benutzerAuthentifkation(benutzerdaten.getBenutzerName(), benutzerdaten.getPasswort());

		return rs;
    }
}