package client.login;


import java.util.TimerTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


public class StatusIntervall extends TimerTask {

    private final String oURL = "xxx"; //ToDo: echte einfügen
    private Client oclient;
    private LoginClient login = new LoginClient();
    /**
     * Sozusagen die Main-Methode, welche von Timer.schedule() alle 10 min aufgerufen wird
     */
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

        return oclient.target(oURL).request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(login.getLoginBenutzer(), MediaType.APPLICATION_JSON));
    }

    /**
     * onlineAuswerten() erhält das Ergebnis von onlineRequest() und wertet dieses aus. 
     * Dann wird dementsprechend reagiert
     *      
     */
    private void onlineAuswerten(Response online) throws JsonMappingException, JsonProcessingException, Exception {
        //response in Object umwandeln,d amit if-Anweisungen funktionieren
        JsonNode node = new ObjectMapper().readTree(online.readEntity(String.class));

        if(node.get(SERVER_ANTWORT).asInt() == 0){
            throw new Exception (
                "Server ist immernoch erreichbar! und sie sind Online"
            );
        }else if(node.get(SERVER_ANTWORT).asInt() != 0){
            //Timeout und neustart der Funktion
        }else{
            throw new Exception(
                "Error! Unbekannter Zustand"
            );
        }
    }


    
}
