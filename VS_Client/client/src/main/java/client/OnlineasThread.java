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

/**
 * 
 * @author Yannik Geber, Alejandro Freyermuth
 *
 */
public class OnlineasThread implements Runnable{

    //private static OnlineasThread oThread;
    private Thread thread;
    private String name;
    private Client oclient;
    private Benutzer masteruser;
    private MasterController mController;
    private final String putURL = "http://localhost:8080/VS_Server/webapi/online"; //zu URL muss noch eine BenutzerID hinzugefügt werden
    

    /**
     * Konstruktor
     * @param masteruser
     * @param mController 
     */
    public OnlineasThread(Benutzer masteruser, MasterController mController, String name){
    	this.name = name;
    	thread = new Thread(this, name);
        this.masteruser = masteruser;
        this.mController = mController;
        thread.start();
    }
    
    
    @Override
    public void run() {
    	while(!thread.isInterrupted()) {
    		try{
        	    Response online = onlineRequest();
        	    System.out.println("Server ist weiterhin erreichbar!");
                onlineAuswerten(online);
                
            } catch(Exception e) {
        	    System.err.println("Server nicht erreichbar! Login erneut erforderlich");
                System.out.println("Sobald der Server wieder erreichbar ist, bitte Anwendung neustarten.");
                try {
					mController.keineSerververbindungNachLogin();
				} catch (InterruptedException e1) {
					System.err.println("Fehler bei Threadinterrupt!");
				}
            }

            
            //Warteintervall
            try{
                thread.sleep(600000); //10 Minuten
            }catch(InterruptedException e){
                System.out.println("Online Abfrage im Hintergrund beendet!");
                break;
            }
    	}
    }
    
    
    public Thread getThread() {
    	return thread;
    }
    
    
    
    /**
     * Starten der Methode run()
     * @param masteruser
     */
    /*
    public static void startOnlineasThread(Benutzer masteruser, MasterController mController){
        oThread = new OnlineasThread(masteruser, mController);
        oThread.start();
    }*/


    /**
     * Beenden des Threads
     */
    /*
    public static void interruptOnlineasThread(){
        if (oThread != null){
            oThread.interrupt();
        }
    }*/

    /**
     * Hauptschleife, welche alle 10 Minuten ausgeführt wird, solange das Programm nicht beendet wird
     */
    /*
    @Override
    public void run(){
        while(!isInterrupted()){
            //Code welcher alle 10 min ausgeführt wird
            try{
        	    Response online = onlineRequest();
        	    System.out.println("Server ist weiterhin erreichbar!");
                //onlineAuswerten(online);

            } catch(Exception e) {
        	    System.err.println("Server nicht erreichbar! Login erneut erforderlich");
                System.out.println("Zurueckgekehren zum Login, sobald Server wieder aktiv!");
                try {
                	mController.neuStart();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            
            //Warteintervall
            try{
                Thread.sleep(30000); //10 Minuten
            }catch(InterruptedException e){
                System.out.println("Online Abfrage im Hintergrund beendet!");
                break;
            }
        }
    }*/


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
            System.out.println("Onlinerückmeldung war erfolgreich.");
        }else{
            throw new Exception(
                "Onlinerückmeldung nicht erfolgreich."
            );
        }
    }

    private String editURL(String pURL){

        return pURL + "/" + masteruser.getBenutzerId();
    }
    
    
}
