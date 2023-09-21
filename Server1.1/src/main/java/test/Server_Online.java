package test;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/online")
public class Server_Online {

	@GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> abfragen()
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            ArrayList<String> benuzterNameList = db.abfrageOnlineBenutzerName();
            db.schliesseDB();
            return benuzterNameList;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
	
	@PUT
    @Path("/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response aktualisieren(@PathParam("benutzerId") int benutzerId)
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.erstelleOnlineEintrag(benutzerId); 
            db.schliesseDB();
            return Response.ok(MediaType.TEXT_PLAIN).build();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Online Status aktualisieren fehlgeschlagen";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}
	
	@DELETE
    @Path("/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response ausloggen(@PathParam("benutzerId") int benutzerId)
	{
        try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.loescheOnlineEintrag(benutzerId);
            db.schliesseDB();
            String nachricht = "Benutzer erfolgreich ausgeloggt";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Benutzer konnte nicht ausgeloggt werden";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}
}