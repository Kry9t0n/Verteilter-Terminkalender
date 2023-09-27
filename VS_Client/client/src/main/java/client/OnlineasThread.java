package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import client.mastercontroller.MasterController;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.lang.Thread;

public class OnlineasThread extends Thread{

    private static OnlineasThread oThread;
    private Client oclient;
    private Benutzer masteruser;
    private MasterController mController;
    private final String putURL = "http://localhost:8080/VS_Server/webapi/online"; //zu URL muss noch eine BenutzerID hinzugefügt werden
    

    /**
     * Konstruktor
     * @param masteruser
     */
    public OnlineasThread(Benutzer masteruser){
        this.masteruser = masteruser;
    }

    /**
     * Starten der Methode run()
     * @param masteruser
     */
    public static void startOnlineasThread(Benutzer masteruser){
        oThread = new OnlineasThread(masteruser);
        oThread.start();
    }


    /**
     * Beenden des Threads
     */
    public static void interruptOnlineasThread(){
        if (oThread != null){
            oThread.interrupt();
        }
    }

    /**
     * Hauptschleife, welche alle 10 Minuten ausgeführt wird, solange das Programm nicht beendet wird
     */
    @Override
    public void run(){
        while(!isInterrupted()){
            //Code welcher alle 10 min ausgeführt wird
            try{
        	    Response online = onlineRequest();
                onlineAuswerten(online);

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch(ProcessingException e) {
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        	    System.out.println("Server nicht erreichbar! Erneute Meldung wenn wieder verfügbar!");
                System.out.println("Erneutes Login nötig!");
=======
        	    System.out.println("Server nicht erreichbar! Login erforderlich, sobald server wieder erreichbar!");
>>>>>>> Stashed changes
=======
        	    System.out.println("Server nicht erreichbar! Login erneut erforderlich");
                System.out.println("Zurueckgekehren zum Login, sobald Server wieder aktiv!");
                try {
                    TestServerAvailable.abfrageStatus();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
>>>>>>> Stashed changes
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }


            //Warteintervall
            try{
                Thread.sleep(595000); //circa 10 Minuten 600000
            }catch(InterruptedException e){
                System.out.println("Online Abfrage im Hintergrund beendet!");
                break;
            }
        }
    }


    /**
     * onlineRequest() wird alle 10 Minuten ausgeführt und überschreib den Eintrag in der Datenbank 
     * des Servers und liefert die Antwort zurück, welche 
     * bei Erfolg 0 ist und bei Misserfolg in ein Timeout läuft
     * @return 
     */
    private Response onlineRequest() throws ProcessingException{
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
            System.out.println("Server ist  erreichbar! und sie sind Online");
        }else{
            throw new Exception(
                "Error!"
            );
        }
    }

    /**
     * Die URL wird um die BenutzerID ergänzt
     */
    private String editURL(String pURL){

        return pURL + "/" + masteruser.getBenutzerId();
    }
    
    
}
