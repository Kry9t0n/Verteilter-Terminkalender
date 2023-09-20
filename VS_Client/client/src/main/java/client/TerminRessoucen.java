package client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class TerminRessoucen {

   //Konstanten
//private static final int STATUS_OK = 200;
	private static final int STATUS_CREATED = 201;
	private static final int STATUS_NO_CONTENT = 204;
    private static final String BASE_URL = "http://localhost:8080/resttest/webapi/termine"; //TODO: für echten Server anpassen
    
	
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
	
    
    public static Response addTermin(Client client, Termin terminToAdd) {
        try {
            Response response = client.target(BASE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(terminToAdd, MediaType.APPLICATION_JSON));

            if (response.getStatus() == STATUS_CREATED) { //TODO: Response weiter verarbeiten
            	return response;
            	//return Response.status(STATUS_CREATED).entity("Termin erfolgsreich hinzugefügt").build(); 	 
            } else { //TODO
            	//return Response.status(response.getStatus()).entity("Fehler beim Hinzufügen des Termins: " 
                //                                            + response.getStatusInfo().getReasonPhrase()).build();
            	return null;
            }
            
         }catch(Exception e) {
        	 e.printStackTrace();
        	 //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Fehler im Server").build(); //TODO
         }
        return null;
    }

    /**
     * Veränderung eines Termins:
     * - Welchen Termin wollen wir verändern (inbesondere die TerminID)
     * TODO: abklären ob Client sich zunächst automatisch den Termin vom Server holt bevor Änderung überhaupt möglich???
     * - POST/PUT ??? Request bei dem das gesamte Terminobjekt mit veränderten Attributen an dern Sever geschickt wird.
     * @param terminID
     * @param terminToUpdate
     * @return
     */
    public static Response updateTermin(Client client, Termin terminToUpdate) {
        try {
            Response response = client.target(BASE_URL)
                    .path("/"+String.valueOf(terminToUpdate.getTerminId()))
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.entity(terminToUpdate, MediaType.APPLICATION_JSON)); //oder POST???

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	//return Response.status(STATUS_CREATED).entity("Termin erfolgsreich aktualisiert").build(); 	 
            	return response;
            } else {
            	//return Response.status(response.getStatus()).entity("Fehler beim Aktualisieren des Termins: " 
                //                                            + response.getStatusInfo().getReasonPhrase()).build();
            	return null;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Error im Server").build();
            return null;
        }
    }
    

    public static Response removeTermin(Client client, int terminId) {
        try {
            Response response = client.target(BASE_URL)
                    .path("/"+String.valueOf(terminId))
                    .request()
                    .delete();

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	//return Response.status(STATUS_NO_CONTENT).entity("Termin erfolgreich entfernt.").build();
            	return response;
            } else {
                //return Response.status(response.getStatus()).entity("Fehler beim Entfernen des Termins: " 
                //											+ response.getStatusInfo().getReasonPhrase()).build();
            	return null;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            //return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Interner Fehler im Server").build();
            return null;
        }
    }
	
    /*
	public static void main (String[] args) {
		Client c = ClientBuilder.newClient();

        // Beispieltermine erstellen
        Termin termin1 = new Termin(1, "GeschäftsTreffen", LocalDateTime.now(), 120, 123, "Alexei");
        Termin termin2 = new Termin(2, "ProjektTreffen", LocalDateTime.now(), 60, 456, "Alejandro");

        // Termin hinzufügen
        Response addResponse1 = TerminRessoucen.addTermin(c, termin1);
        Response addResponse2 = TerminRessoucen.addTermin(c, termin2);
        
        if(addResponse1 != null && addResponse2 != null) {
            System.out.println(addResponse1.getEntity());
            System.out.println(addResponse2.getEntity());
        }


        // Termin aktualisieren
        termin1.setTitel("Neues Meeting 1");
        Response updateResponse1 = null;
		try {
			updateResponse1 = TerminRessoucen.updateTermin(c, termin1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(updateResponse1 != null) {
			System.out.println(updateResponse1.getEntity());
		}
        

        // Termin entfernen
        Response removeResponse1 = TerminRessoucen.removeTermin(c, termin1.getTerminId());
        Response removeResponse2 = TerminRessoucen.removeTermin(c, termin2.getTerminId());
        
        if(removeResponse1 != null && removeResponse2 != null) {
        	System.out.println(removeResponse1.getEntity());
            System.out.println(removeResponse2.getEntity());
        }
        
		
	}*/
}
