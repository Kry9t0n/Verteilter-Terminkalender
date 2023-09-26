package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class OnlineasThread extends Thread{

    private static OnlineasThread oThread;
    private Client oclient;
    private Benutzer masteruser;
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
        	    System.out.println("Server nicht erreichbar! Versuche in 10 min wieder.");
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }


            //Warteintervall
            try{
                Thread.sleep(600000); //10 Minuten
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
            System.out.println("Server ist immernoch erreichbar! und sie sind Online");
        }else{
            throw new Exception(
                "Error! Erneuter Versuch in 10 Minuten"
            );
        }
    }

    private String editURL(String pURL){

        return pURL + "/" + masteruser.getBenutzerId();
    }
    
    
}
