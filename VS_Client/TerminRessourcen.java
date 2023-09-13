package client;

import org.glassfish.jersey.client.ClientConfig;
import java.util.Date;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Path;


@Path("/Termine")
public class TerminRessourcen {

   //Konstanten
//private static final int STATUS_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final int STATUS_NO_CONTENT = 204;
    private static final String BASE_URL = "http://localhost:8080/resttest/webapi/Benutzer/Termine";
    
	//Attributen
	private Client client;
	
	//leeres Konstruktor
	public TerminRessourcen() {
        ClientConfig clientConfig = new ClientConfig();
        this.client = ClientBuilder.newClient(clientConfig);
    }
	
	/** TODO
	 * GET Http Request send to the server to obtain a Resource or many from the BASE_URL 
	 * @Produces(Mediatype.APPLICATION.JSON) 
	 * client.target(BASE_URL).path("Termine").
                              .path("TerminId") 
                              .request()
                              .accept(MediaType.APPLICATION.JSON).
                              .get(Termin.class)
                              .toString()
	 */
	
    
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    private Response addTermin(Termin terminToAdd) {
        try {
            Response response = client.target(BASE_URL)
                    .path("termine")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(terminToAdd, MediaType.APPLICATION_JSON));

            if (response.getStatus() == STATUS_CREATED) {
            	return Response.status(STATUS_CREATED).entity("Termin erfolgsreich hinzugefügt").build(); 	 
            } else {
            	return Response.status(response.getStatus()).entity("Fehler beim Hinzufügen des Termins: " 
                                                            + response.getStatusInfo().getReasonPhrase()).build();
            }
            
         }catch(Exception e) {
        	 e.printStackTrace();
        	 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Fehler im Server").build();
         }	         
    }

	@PUT
	@Path("/{terminId}")
    private Response updateTermin(@PathParam("terminId") int terminID,Termin terminToUpdate) {
        try {
            Response response = client.target(BASE_URL)
                    .path("termine")
                    .path(String.valueOf(terminToUpdate.getTerminId()))
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(terminToUpdate, MediaType.APPLICATION_JSON));

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	return Response.status(STATUS_CREATED).entity("Termin erfolgsreich aktualisiert").build(); 	 
            } else {
            	return Response.status(response.getStatus()).entity("Fehler beim Aktualisieren des Termins: " 
                                                            + response.getStatusInfo().getReasonPhrase()).build();
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Error im Server").build();
        }
    }
    
	@DELETE
    @Path("/{terminId}")
    private Response removeTermin(@PathParam("terminId") int terminId) {
        try {
            Response response = client.target(BASE_URL)
                    .path("termine")
                    .path(String.valueOf(terminId))
                    .request()
                    .delete();

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	return Response.status(STATUS_NO_CONTENT).entity("Termin erfolgreich entfernt.").build();
            } else {
                return Response.status(response.getStatus()).entity("Fehler beim Entfernen des Termins: " 
                											+ response.getStatusInfo().getReasonPhrase()).build();
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Fehler im Server").build();
        }
    }
	
	public static void main (String[] args) {
		TerminRessourcen terminRessourcen = new TerminRessourcen();

        // Beispieltermine erstellen
        Termin termin1 = new Termin(1, "GeschäftsTreffen", new Date(), 120, 123, "Alexei");
        Termin termin2 = new Termin(2, "ProjektTreffen", new Date(), 60, 456, "Alejandro");

        // Termin hinzufügen
        Response addResponse1 = terminRessourcen.addTermin(termin1);
        Response addResponse2 = terminRessourcen.addTermin(termin2);

        System.out.println(addResponse1.getEntity());
        System.out.println(addResponse2.getEntity());

        // Termin aktualisieren
        termin1.setTitel("Neues Meeting 1");
        Response updateResponse1 = terminRessourcen.updateTermin(termin1.getTerminId(), termin1);
        System.out.println(updateResponse1.getEntity());

        // Termin entfernen
        Response removeResponse1 = terminRessourcen.removeTermin(termin1.getTerminId());
        Response removeResponse2 = terminRessourcen.removeTermin(termin2.getTerminId());
        
        System.out.println(removeResponse1.getEntity());
        System.out.println(removeResponse2.getEntity());
		
	}
}
