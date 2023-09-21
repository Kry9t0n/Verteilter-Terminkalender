package client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.GenericType;

/**
 * Die Klasse TerminRessourcen kümmert sich um das Abfragen,das Ändern und das Löschen von Ressourcen 
 * durch Http Requests an der Server, wo diese danach auch an der TerminTabelle ausgeführt wird.
 */
public class TerminRessoucen {

    //Konstanten
    private static final int STATUS_OK = 200;	
    private static final int STATUS_CREATED = 201;
    private static final int STATUS_NO_CONTENT = 204; 
	
    private static final String BASE_URL = "http://localhost:8080/VS_Server/webapi/termin"; //angepasste Url Server
    	
    /** 
     * Anfrage Zum Erhalten eines bestimmten Termins mittels TerminId
     * GET Http Request send to the server through a URL
     * @param client
     * @param terminId
     * @return einen Termin Objekt
     */
     public static Termin getEinzelTerminByID(Client client, int terminId) { 
	try {
	    Response response = client.target(BASE_URL)
                .path("/" + String.valueOf(terminId))
                .request(MediaType.APPLICATION_JSON)
                .get(Termin.class); 	
	     	
	    if (response.getStatus() == STATUS_OK) {
                Termin EinTermin = response.readEntity(Termin.class);
		return EinTermin;    
            } else {
                System.out.println("Fehler beim Abrufen des einzelnen Termins: " + response.getStatusInfo().getReasonPhrase());
                return null;
            }
		
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }    	     
     }	

     /**
      * Anfragen Zum Bekommen alle Terminedaten 
      * GET Http Request send to the server through a URL
      * @param client
      * @return eine Liste von allen Terminen 
      */
     public static List<Termin> getAllTermine(Client client) {
	try {
	    Response response = client.target(BASE_URL + "") 
		    .request(Mediatype.APPLICATION_JSON)
		    .get();
		
	    if (response.getStatus() == STATUS_OK) {
                // Die Antwortdaten in eine Liste von Termin-Objekten deserialisieren
                List<Termin> termineListe = response.readEntity(new GenericType<List<Termin>>() {});
		return termineListe;    
            } else {
                // Fehlermeldung ausgeben, wenn die Anfrage nicht erfolgreich war
                System.out.println("Fehler beim Abrufen aller Termine: " + response.getStatusInfo().getReasonPhrase());
                return null;
            }
		
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     }	
	
     /**
      * Hinzufügen eines Termins:
      * @param client
      * @param terminToAdd
      * @return eine response Instanz
      */
     public static Response addTermin(Client client, Termin terminToAdd) {
        try {
            Response response = client.target(BASE_URL + "")
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(terminToAdd, MediaType.APPLICATION_JSON));

            if (response.getStatus() == STATUS_CREATED) { 
		Termin createdTermin = response.readEntity(Termin.class);
                System.out.println("That was successful!");    
            	return response; 	
            } else {
		System.out.println(response.getStatusInfo().getReasonPhrase());    
             	return null;
            }
            
         }catch(Exception e) {
        	 e.printStackTrace();
        	 return null;
         }       
    }

    /**
     * Veränderung eines Termins:
     * @param client
     * @param terminID
     * @param terminToUpdate
     * @return eine response Instanz
     */
    public static Response updateTermin(Client client, Termin terminToUpdate) {
        try {
            int terminId = terminToUpdate.getTerminId();
            Termin neuerTermin = getEinzelTermin(client, terminId);

            if (neuerTermin == null) {
	        System.out.println("Der Termin mit ID " + terminId + " wurde nicht gefunden.");
	        return null;
            }
		
            Response response = client.target(BASE_URL)
                    .path("/"+String.valueOf(terminId))
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(neuerTermin, MediaType.APPLICATION_JSON)); 

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	System.out.println("That was successful!");	 
            	return response;
            } else {
            	System.out.println("Fehler beim Aktualisieren des Termins "+ response.getStatusInfo().getReasonPhrase());
            	return null;
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Löschen eines Termins anhand der TerminId
     * @param client
     * @param terminId
     * @return eine response instanz
     */
    public static Response removeTermin(Client client, int terminId) {
        try {
            Response response = client.target(BASE_URL)
                    .path("/"+String.valueOf(terminId))
                    .request()
                    .delete();

            if (response.getStatus() == STATUS_NO_CONTENT) {
            	System.out.println("That was successful!");
            	return response;
            } else {
		System.out.println(response.getStatusInfo().getReasonPhrase());    
		return null;    
            }
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
    /**
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
        
	         // Einzelnen Termin abrufen
	        int terminIdToRetrieve = 1;
	        Termin einzelTermin = TerminRessoucen.getEinzelTermin(c, terminIdToRetrieve);
	 
	        if (einzelTermin != null) {
	            System.out.println("Der abgerufene Termin:");
	            System.out.println("Termin ID: " + einzelTermin.getTerminId());
	            System.out.println("Titel: " + einzelTermin.getTitel());
	            System.out.println("Datum: " + einzelTermin.getDatum());
		    System.out.println("Dauer: " + einzelTermin.getDauer()); 
	            System.out.println("ErstellerId: " + einzelTermin.getIdErsteller());
		    System.out.println("EingeladenBenutzer: " + einzelTermin.getBenutzerEingeladen());
	        }
	
	        // Alle Termine abrufen
	        List<Termin> alleTermine = TerminRessoucen.getAllTermine(c);
	        if (alleTermine != null) {
	            System.out.println("Alle abgerufenen Termine:");
	            for (Termin termin : alleTermine) {
	                System.out.println("Termin ID: " + termin.getTerminId());
	                System.out.println("Titel: " + termin.getTitel());
	                System.out.println("Datum: " + termin.getDatum());
		        System.out.println("Dauer: " + termin.getDauer()); 
	                System.out.println("ErstellerId: " + termin.getIdErsteller());
		        System.out.println("EingeladenBenutzer: " + termin.getBenutzerEingeladen());
	            }
	        }	
	}
     */
}
