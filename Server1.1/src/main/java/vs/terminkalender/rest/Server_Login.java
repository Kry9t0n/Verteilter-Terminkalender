package vs.terminkalender.rest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vs.terminkalender.database.DB_Funktionen;
import vs.terminkalender.datatypes.Benutzer;

/**
 * @Autor Niklas Baldauf, Maik Girlinger, Niklas Balke, Justin Witsch
 * @version 1.1
 */
@Path("/login")
public class Server_Login 
{
	
	private final Benutzer failedBenutzer = new Benutzer(-1, "-1", "-1", "-1","-1", -1);
	
	/**
	 * Anmeldung des Benutzers über /login mit JSON-Übergabe der Benutzerdaten
	 * @param benutzerdaten
	 * @return
	 */
	@POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response anmelden(Benutzer benutzerdaten) 
	{	
        return Response.ok(checkAuthentication(benutzerdaten), MediaType.APPLICATION_JSON).build();
    }
	
	/**
	 * Authentifizierung des Benutzers
	 * @param benutzerdaten
	 * @return alle Benutzerdaten, wenn erfolgreich
	 * 		   failedBneutzer, der oben deklariert ist, wenn nicht erfolgreich
	 */
    private Benutzer checkAuthentication(Benutzer benutzerdaten) 
    {
		Benutzer benutzerdatenTemp = null;

    	DB_Funktionen db = new DB_Funktionen("SA","");
		db.oeffneDB();

	    benutzerdatenTemp = db.benutzerAuthentifkationAlleAttribute(benutzerdaten.getBenutzerName(), benutzerdaten.getPasswort());
	    
		if (benutzerdatenTemp != null  || benutzerdatenTemp != failedBenutzer)
		{
			//Wenn Einloggen erfolgreich, wird Online Eintrag erstellt
			db.erstelleOnlineEintrag(benutzerdatenTemp.getBenutzerId());
		}
		
		db.schliesseDB();

		return benutzerdatenTemp;
    }
}
