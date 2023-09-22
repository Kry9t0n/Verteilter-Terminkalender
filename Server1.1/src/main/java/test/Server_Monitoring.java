package test;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/monitoring")
public class Server_Monitoring 
{
	/**
	 * GET Call zur Abfrage von Informationen über den Tomcat Server 
	 * @return Daten als Monitoring_Data Datentyp
	 */
	@GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
	public Monitoring_Data abfragen()
	{
		try 
        {
			Monitoring_Data data = Monitoring.serverInfos();
			return data;
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        } 
	}
}
