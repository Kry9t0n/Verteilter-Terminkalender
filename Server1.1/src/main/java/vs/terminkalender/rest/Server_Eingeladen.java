package vs.terminkalender.rest;
import java.util.ArrayList;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vs.terminkalender.database.DB_Funktionen;
import vs.terminkalender.datatypes.Benutzer;
import vs.terminkalender.datatypes.Termin;

/**
 * @Autor Niklas Baldauf, Maik Girlinger, Niklas Balke, Justin Witsch
 * @version 1.1
 */
@Path("/eingeladen")
public class Server_Eingeladen 
{
	
	/**
	 * Einfuegen eines Benutzer-, Terminpaares in die Eingeladen-Tabelle ueber /eingeladen/terminId/benutzerId
	 * @param terminId
	 * @param benutzerId
	 * @return Response ok, wenn erfolgreich
	 * 		   Response status, wenn Fehler
	 */
	@POST
    @Path("/{terminId}/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response einladen(@PathParam("terminId") int terminId, @PathParam("benutzerId") int benutzerId) 
	{
		try
		{
			DB_Funktionen db = new DB_Funktionen("SA","");
			db.oeffneDB();
			db.erstelleEintragEingeladen(benutzerId, terminId, null);
			db.schliesseDB();
			String nachricht = "Eintrag in Termin und Eingelanden wurde erstellt";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
            String nachricht = "Fehler beim Erstellen des Eintrags";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
		}	
	}
	
	/**
	 * Loeschen eines Benutzer-, Terminpaares in die Eingeladen-Tabelle ueber /eingeladen/terminId/benutzerId
	 * @param terminId
	 * @param benutzerId
	 * @return Response ok, wenn erfolgreich
	 * 		   Response status, wenn Fehler
	 */
	@DELETE
    @Path("/{terminId}/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response loeschen(@PathParam("terminId") int terminId, @PathParam("benutzerId") int benutzerId) 
	{
        try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.loescheEintragEingeladenAnhandBenutzerIdTerminId(benutzerId, terminId);
            db.schliesseDB();
            String nachricht = "Eintrag wurde gelöscht";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Löschen des Eintrags";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}
	
	/**
	 * Ausgabe aller Termine, zu dem ein Benutzer eingeladen ist ueber /eingeladen/benutzer/benutzerId
	 * @param benutzerId
	 * @return ArrayList<Termin> aller Termine, wenn erfolgreich
	 * 		   null, wenn Fehler
	 */
	@GET
    @Path("/benutzer/{benutzerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Termin> abfrageEingeladenetermine(@PathParam("benutzerId") int benutzerId) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            ArrayList<Termin> terminList = db.abfrageEingeladenetermine(benutzerId);
            db.schliesseDB();
            return terminList;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
	
	/**
	 * Ausgabe aller Benutzer, die zu einem Termin eingeladen sind ueber /eingeladen/benutzer/benutzerId
	 * @param benutzerId
	 * @return ArrayList<Benutzer> aller Benutzer, wenn erfolgreich
	 * 		   null, wenn Fehler
	 */
	@GET
    @Path("/termin/{terminId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Benutzer> abfragenAllerEingeladenen(@PathParam("terminId") int terminId) 
	{
		ArrayList<Benutzer> benutzerList = new ArrayList<Benutzer>();
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            benutzerList = db.sucheAlleBenutzerMitTerminIdAusEingeladen(terminId);
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