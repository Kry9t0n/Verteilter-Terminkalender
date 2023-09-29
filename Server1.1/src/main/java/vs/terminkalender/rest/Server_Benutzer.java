package vs.terminkalender.rest;

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
import vs.terminkalender.database.DB_Funktionen;
import vs.terminkalender.datatypes.Benutzer;

@Path("/benutzer")
public class Server_Benutzer 
{
	/**
	 * Erstellen eines Benutzers über /benutzer und den JSON-Benutzerdaten
	 * @param benutzerdaten
	 * @return	Response ok, wenn erfolgreich
	 * 			Response status, wenn Fehler
	 */
	@POST
    @Path("")
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
	
	/**
	 * Aendern eines Benutzers über /benutzer und den JSON-Benutzerdaten (Aenderung anhand der BenutzerId)
	 * @param benutzer
	 * @return Response ok, wenn erfolgreich
	 * 		   Response status, wenn Fehler
	 */
	@PUT
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response benutzerAendern(Benutzer benutzer) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.aendereBenutzer(benutzer);
            db.schliesseDB();
            String nachricht = "Benutzer wurde geändert";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Ändern des Benutzers";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}  
	
	/**
	 * Ausgeben aller Benutzer, die in der DB drin stehen ueber /benutzer
	 * @return ArrayList<Benutzer> --> Liste aller Benutzer
	 */
	@GET
    @Path("")
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
	
	/**
	 * Ausgeben eines Benutzers, anhand der uebergebenen BenutzerId ueber /benutzer/benutzerId
	 * @param benutzerId
	 * @return Benutzer-Objekt mit allen Attributen
	 */
	@GET
    @Path("/{benutzerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Benutzer abfragenEinesBenutzers(@PathParam("benutzerId") int benutzerId) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            Benutzer benutzer = db.sucheBenutzerMitBenutzerId(benutzerId);
            db.schliesseDB();
            return benutzer;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
	
	/**
	 * Loeschen eines Benutzers, anhand der uebergebenen BenutzerId ueber /benutzer/benutzerId
	 * @param benutzerId
	 * @return Response ok, wenn erfolgreich
	 * 		   Response status, wenn Fehler
	 */
	@DELETE
    @Path("/{benutzerId}")
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
}
