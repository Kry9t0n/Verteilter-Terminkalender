package test;

import java.util.ArrayList;

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

@Path("/admin")
public class Server_Admin 
{
	@POST
    @Path("/benutzerErstellen")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response benutzerErstellen(Benutzer benutzerdaten) 
	{	
		try
		{
			DB_Funktionen db = new DB_Funktionen("SA","");
			db.oeffneDB();
			db.erstelleBenutzer(benutzerdaten);
			db.schliesseDB();
			String nachricht = "Benutzer wurde erstellt";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
		}
		catch(Exception e)
		{
			e.printStackTrace();
            String nachricht = "Fehler beim Erstellen des Benutzers";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
		}	
    }
	
	@PUT
	@Path("/benutzerAendern")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response benutzerAendern(Benutzer benutzer) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.aendereBenutzer(benutzer);
            db.schliesseDB();
            String nachricht = "Termin wurde geändert";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Ändern des Termins";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}  
	
	@DELETE
    @Path("/loeschen/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response loeschen(@PathParam("benutzerId") int benutzerId) 
	{
        try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.loescheBenutzer(benutzerId);
            db.schliesseDB();
            String nachricht = "Benutzer wurde gelöscht";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Löschen des Benutzers";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}
	
	@GET
    @Path("/abfragenAlleBenutzer")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Benutzer> abfragenAlleBenutzer() 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            ArrayList<Benutzer> benutzerList = db.gibAlleBenutzer();
            db.schliesseDB();
            return benutzerList;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
	
	
}
