package client;

import java.util.Timer;

import client.mastercontroller.MasterController;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class TestServerAvailable {
    
    private final static String GETURL = "http://localhost:8080/VS_Server/webapi/Monitoring/erreichbar";
    private static Client abfragClient;
    private static Timer timer = new Timer();
    private static MasterController mController;

    public static void abfrageStatus() throws InterruptedException{
        //wenn Server erreichbar dann starte Login, falls nicht warte 30sec und prüf wieder

        try{
        Response status = abfrageDurchfuehren();
        abfragePruefen(status);
        
        } catch(ProcessingException e) {
        	    System.out.println("Server nicht erreichbar! Erneuter Versuch in 30sec!");
                timer.wait(30000);
                mController = new MasterController(); //Neustart des Programms
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

    private static void abfragePruefen(Response status) throws Exception{
        
        if(status.getStatus() == Response.Status.OK.getStatusCode()){
            System.out.println("Server ist bereit für das Login!\n");
        }else{
            throw new Exception(
                "Error! Unbekannter Status"
            );
        }

    }


}
