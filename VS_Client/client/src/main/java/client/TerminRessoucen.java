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

/**
 * Die Klasse TerminRessourcen kümmert sich um das Abfragen,das Ändern und das Löschen von Ressourcen 
 * durch Http Requests an der Server, wo diese danach auch an der TerminTabelle ausgeführt wird.
 */
public class TerminRessoucen {

    //Konstanten
    private static final int STATUS_CREATED = 201;
    private static final int STATUS_NO_CONTENT = 204;
    private static final String BASE_URL = "http://localhost:8080/VS_Server/webapi/Termin"; //angepasste Url Server
    	
    /** 
     * GET Http Request send to the server through a URL
     * Diese Methode  gibt 
     */
     public static Termin getEinzelTerminByID(Client client, int terminId) { 
	try {
	     	
	} catch(Exception e) {
		
	}    
	     
     }	
    
     public static Response addTermin(Client client, Termin terminToAdd) {
        try {
            Response response = client.target(BASE_URL)
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(terminToAdd, MediaType.APPLICATION_JSON));

            if (response.getStatus() == STATUS_CREATED) { 
		Termin createdTermin = response.readEntity(Termin.class);
                System.out.println("Termin wurde erfolgreich erstellt:");
                System.out.println("Termin ID: " + createdTermin.getTerminId());
                System.out.println("Titel: " + createdTermin.getTitel());
                System.out.println();
            	return response; 	
            } else {
		System.out.println("Fehler beim Hinzufügen des Termins " + response.getStatusInfo().getReasonPhrase());    
             	return null;
            }
            
         }catch(Exception e) {
        	 e.printStackTrace();
        	 return null;
         }       
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
                    .post(Entity.entity(terminToUpdate, MediaType.APPLICATION_JSON)); 

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	System.out.println("Termin wurde erfolgreich aktualisiert!");	 
            	return response;
            } else {
            	System.out.println("Fehler beim Aktualisieren des Termins " + response.getStatusInfo().getReasonPhrase());
            	return null;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
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
            	System.out.println("Termin wurde erfolgreich entfernt!");
            	return response;
            } else {
		System.out.println("Fehler beim Entfernen des Termins: " + response.getStatusInfo().getReasonPhrase());    
		return null;    
            }
             
        } catch (Exception e) {
            e.printStackTrace();
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
