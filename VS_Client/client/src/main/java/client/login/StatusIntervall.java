package client.login;


import java.util.TimerTask;

import jakarta.ws.rs.core.Response;

public class StatusIntervall extends TimerTask {

    private final String oURL = "xxx"; //ToDo: echte einfügen
    
    /**
     * Sozusagen die Main-Methode, welche von Timer.schedule() aufgerufen wird
     */
    @Override
    public void run() {
        Response online = onlineRequest();
        onlineAuswerten(online);

    }

    /**
     * onlineRequest() wird alle 10 Minuten ausgeführt und überschreib den Eintrag in der Datenbank des Servers und liefert die Antwort zurück, welche 
     * bei Erfolg 0 ist und bei Misserfolg in ein Timeout läuft
     * @return 
     */
    private Response onlineRequest(){

        return 
    }

    /**
     * onlineAuswerten() erhält das Ergebnis von onlineRequest() und wertet dieses aus. Dann wird dementsprechend reagiert
     *      
     */
    private void onlineAuswerten(Response online){
        //response in Object umwandeln,d amit if-Anweisungen funktionieren
    }


    
}
