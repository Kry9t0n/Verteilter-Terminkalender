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


@Path("/termin")
public class Server_Termin
{
	@POST
    @Path("/erstellen")
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
	
	@POST
    @Path("/einladen/{terminId}/{benutzerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response einladen(@PathParam("terminId") int terminId, @PathParam("benutzerId") int benutzerId) 
	{
		try
		{
			DB_Funktionen db = new DB_Funktionen("SA","");
			db.oeffneDB();
			db.erstelleEintragEingeladen(benutzerId, terminId, "??TEST??"); //Warum Info??
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
	
	@DELETE
    @Path("/loeschen/{terminId}/{benutzerId}")
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
	
	@GET
    @Path("/abfrageEingeladenetermine/{benutzerId}")
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
	
	@GET
    @Path("/abfragenEigeneTermine/{benutzerId}/{datum}")
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
	
	@GET
    @Path("/abfragenAlleTermine")
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
	
	@GET
    @Path("/abfrageEinzelTermin/{terminId}")
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
	
	@PUT
	@Path("/terminAendern")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response terminAendern(Termin termindaten) 
	{
		try 
        {
        	DB_Funktionen db = new DB_Funktionen("SA", "");
            db.oeffneDB();
            db.aendereTermin(termindaten);
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
	
	
	
}
