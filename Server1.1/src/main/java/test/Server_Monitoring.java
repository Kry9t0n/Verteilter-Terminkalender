package test;

import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/monitoring")
public class Server_Monitoring 
{
	@GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
	public Monitoring_Data abfragen()
	{
		try 
        {
			Monitoring_Data data = Monitoring.serverInfos();
			System.out.println("Abfrage wird durchgeführt in Rest Klasse");
			return data;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
}
