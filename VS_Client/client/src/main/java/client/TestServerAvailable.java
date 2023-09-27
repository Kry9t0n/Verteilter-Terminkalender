package client;

import java.util.Timer;

import client.mastercontroller.MasterController;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TestServerAvailable {
    
    private final static String GETURL = "http://localhost:8080/VS_Server/webapi/monitoring/erreichbar";
    private static Client abfragClient;
    private static Timer timer = new Timer();

    public static void abfrageStatus(MasterController controller) throws InterruptedException{
        //wenn Server erreichbar dann starte Login, falls nicht warte 30sec und prüf wieder
        try{
        Response status = abfrageDurchfuehren();
        abfragePruefen(status, controller);
        
        } catch(ProcessingException e) {
        	    System.out.println("Server nicht erreichbar! Erneuter Versuch in 30sec!");
        	    Thread.currentThread().sleep(30000);
        }catch (Exception e) {
                System.out.println(e.getMessage());
        }

    }

    private static Response abfrageDurchfuehren() throws ProcessingException{
        abfragClient = ClientBuilder.newClient();

        return abfragClient.target(GETURL)
                .request(MediaType.APPLICATION_JSON)
                .get();
    }

    private static void abfragePruefen(Response status, MasterController controller) throws Exception{
        
        if(status.getStatus() == Response.Status.OK.getStatusCode()){
            System.out.println("Server ist bereit für das Login!\n");
            
            controller.setServerErreichbar(true);
            controller.run();
        }else{
            throw new Exception(
                "Error! Unbekannter Status"
            );
        }

    }


}
