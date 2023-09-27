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
import vs.terminkalender.datatypes.Termin;


@Path("/termin")
public class Server_Termin
{
	
	/**
     * Erhält Termindaten und legt Eintrag in der Termin & Eingeladen Tabelle an
     * @param termindaten
     * @return Response ob Eintrag erfolgreich
     */
    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response aendern(Termin termindaten) 
    {
        try
        {
            DB_Funktionen db = new DB_Funktionen("SA","");
            db.oeffneDB();
            db.aendereTermin(termindaten);
            db.schliesseDB();
            String nachricht = "Eintrag in Termin und Eingeladen wurde geeandert";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Aendern des Eintrags";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        }
    }
	
	/**
	 * Erhält Termindaten und legt Eintrag in der Termin & Eingeladen Tabelle an
	 * @param termindaten
	 * @return Response ob Eintrag erfolgreich
	 */
	@POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response erstellen(Termin termindaten) 
	{
		try
		{
			DB_Funktionen db = new DB_Funktionen("SA","");
			db.oeffneDB();
			db.erstelleTerminUndEintragEingeladenAnhandBenutzerName(termindaten);
			db.schliesseDB();
			String nachricht = "Eintrag in Termin und Eingeladen wurde erstellt";
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
	 * Gibt alle Einträge der Termin Tabelle zurück
	 * @return Termine als ArrayList
	 */
	@GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Termin> abfragenAlleTermine() 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            ArrayList<Termin> terminList = db.sucheAlleTermine();
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
	 * Entfernt einen Eintrag aus der Termintabelle anhand der terminId
	 * @param terminId des zu löschenden Termin
	 * @return Response ob Löschen erfolgreich
	 */
	@DELETE
    @Path("/{terminId}")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response loeschenTermin(@PathParam("terminId") int terminId) 
	{
        try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.loescheTermin(terminId);
            db.schliesseDB();
            String nachricht = "Termin wurde gelöscht";
            return Response.ok(nachricht, MediaType.TEXT_PLAIN).build();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            String nachricht = "Fehler beim Löschen des Termins";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(nachricht).build();
        } 
	}
	/**
	 * Gibt den Eintrag mit der übergebenen terminId aus der Termin Tabelle
	 * @param terminId des zurückzugebenden Termins
	 * @return Termine als ArrayList
	 */
	@GET
    @Path("{terminId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Termin abfrageEinzelTermin(@PathParam("terminId") int terminId) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            Termin termin = db.sucheTerminMitTerminId(terminId);
            db.schliesseDB();
            return termin;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
	/**
	 * Gibt die Termine eines Benutzers an dem angefragten Datum zurück
	 * @param benutzerId
	 * @param datum
	 * @return ArrayList mit Terminen
	 */
	@GET
    @Path("/{benutzerId}/{datum}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Termin> abfragenEigeneTermine(@PathParam("benutzerId") int benutzerId, @PathParam("datum") String datum) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            ArrayList<Termin> terminList = db.sucheTerminMitBenutzerId(benutzerId, datum);
            db.schliesseDB();
            return terminList;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
}
