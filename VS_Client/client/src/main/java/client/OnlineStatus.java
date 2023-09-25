package client;

import java.util.TimerTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Intervall, welches alle 10 Minuten ausgeführt wird, um den OnlineStatus zu überprüfen. Erfolgreich ist die Abfrage
 * , wenn der Server erreichbar ist und mit der Message: "Online Status aktualisiert" antwortet. 
 * Falls eine andere Antwort kommt, wird nach 10 Minuten ein neuer Versuch gestartet, ist der Server gar nicht erst erreichbar
 * wird die entsprechende Exception geworfen.
 * 
 *  @author Yannik Geber
 */


public class OnlineStatus extends TimerTask {

    //Attribute
    private Client oclient;
    private Benutzer masteruser;
    private final String putURL = "https://localhost:8080/VS_Server/webapi/online"; //zu URL muss noch eine BenutzerID hinzugefügt werden

    
    public OnlineStatus(Benutzer master){
        this.masteruser = master;
    }

    @Override
    public void run() {
        Response online = onlineRequest();
        try{
            onlineAuswerten(online);

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * onlineRequest() wird alle 10 Minuten ausgeführt und überschreib den Eintrag in der Datenbank 
     * des Servers und liefert die Antwort zurück, welche 
     * bei Erfolg 0 ist und bei Misserfolg in ein Timeout läuft
     * @return 
     */
    private Response onlineRequest(){
        oclient = ClientBuilder.newClient();
        String modURL = editURL(putURL);

        return oclient.target(modURL).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(masteruser, MediaType.APPLICATION_JSON));
    }

    /**
     * onlineAuswerten() erhält das Ergebnis von onlineRequest() und wertet dieses aus. 
     * Dann wird dementsprechend reagiert
     *      
     */
    private void onlineAuswerten(Response online) throws JsonMappingException, JsonProcessingException, Exception {
        
        
        //Überprüfung auf den Response Code 200 = OK, anstatt auf Nachricht
        if(online.getStatus() == Response.Status.OK.getStatusCode()){
            throw new Exception (
                "Server ist immernoch erreichbar! und sie sind Online"
            );
        }else{
            throw new Exception(
                "Error! Erneuter Versuch in 10 Minuten"
            );
        }
        
        
        //response in Object umwandeln,d amit if-Anweisungen funktionieren
        /* 
        JsonNode node = new ObjectMapper().readTree(online.readEntity(String.class));

        if(node.get("nachricht").toString().equals("Online Status aktualisiert")){
            throw new Exception (
                "Server ist immernoch erreichbar! und sie sind Online"
            );
        }else{
            throw new Exception(
                "Error! Unbekannter Zustand"
            );
        }
        */
    }



    private String editURL(String pURL){

        return pURL + "/" + masteruser.getBenutzerId();
    }


    
}
