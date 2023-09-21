package test;
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


@Path("/termin")
public class Server_Termin
{
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
