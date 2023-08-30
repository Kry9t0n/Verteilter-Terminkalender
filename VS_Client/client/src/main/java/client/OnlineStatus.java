package client;

import org.glassfish.jersey.client.JerseyClientBuilder;
import org.json.JSONObject;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class OnlineStatus {
    private final String oURL = ""; //has to be set
    private JSONObject status;
    private boolean online = true;

    

/**
 * Beim Login soll der Client sich beim Server als Online melden (aufruf der Methode bei Login), dadurch
 * wird der Client automatisch in die Table geschrieben mit allen online Clients
 * -> zum Einloggen ein Post-Request an das Verzeichnis
 */
    public Response onlineMelden(Client client){
        status = new JSONObject();
        status.put("Onlinestatus", online);
        
        Response res = client.target(oURL).request().post(Entity.xml(status));
        return res;
    } 

/**
 * Die Server Response muss überprüft werden und ggf. eine Meldung erfolgen, dass man Online ist
 */
    private void checkServerResponse(Response res){
        
    }

/**
 * Beim Ausloggen soll der Client sich beim Server abmelden (aufruf der Methode bei Logout), dadurch
 * wird der Client automatisch aus der Tabelle gelöscht (nicht mehr online)
 * -> zum abmelden DELETE-Request
 */
    public Response onlineDelete(Client client){
        boolean nochaktiv = getOnline();
        if(nochaktiv = false){
            System.out.println("Client ist bereits abgemeldet!\n");
        }else{
            status = new JSONObject();
            status.put("Onlinestatus", nochaktiv);

            WebTarget target = client.target(oURL);
            target.request().accept(MediaType.APPLICATION_JSON).method("DELETE", status); //korrekt?
        }
    }
    
/**
 * 10 Minuten Intervall an Server um dessen Status abzufragen -> Bist du noch da?
 * Server antwortet mit Value 1 -> muss ausgewertet werden
 * Außerdem geht dann der Server davon aus, dass der Client ebenfalls noch online ist
 * Server löscht CLient aus online User, wenn intervall zweimal verpasst
 */


    public boolean getOnline(){
        return this.online;
    }
}
