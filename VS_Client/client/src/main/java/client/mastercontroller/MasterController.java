package client.mastercontroller;

import client.Benutzer;
import client.OnlineasThread;
import client.Admin.AdminClient;
import client.Admin.AdminDialog;
import client.Client.BenutzerClient;
import client.Client.ClientDialog;
import client.login.LoginDialog;
import client.TestServerAvailable;
import java.lang.Thread;


/**
 * @author Alejandro Freyermuth
 * Der MasterController ist dazu da, den gesamten clientseitigen
 * Programmablauf zu steuern sowie das Benutzerobjekt zu verwalten.
 * Das heißt der MasterController stellt den Eintrittspunkt in das 
 * Programm dar und startet IMMER als erste Aktion den Login. 
 * Danach entscheidet er, ob der Benutzerclient oder der Admin-Client
 * gestartet aus. 
 *
 */
public class MasterController {
	private final int IS_ADMIN = 1;
	private final int NOT_ADMIN = 0;
	
	private Benutzer masterUser;
	
	
	
	public MasterController() throws InterruptedException {
		masterUser = new Benutzer();
		run();
	}
	
	private void run() throws InterruptedException {
		TestServerAvailable.abfrageStatus();
		fuehreLoginClientAus();
		//Start des HintergrundThreads zur Abfrage des OnlineStatus (ausgelagert)
		OnlineasThread.startOnlineasThread(masterUser);
		selectAndRunClient();
	}
	
	private void fuehreLoginClientAus() {
		LoginDialog ld = new LoginDialog(masterUser);
	}
	
	private void selectAndRunClient() {
		if(masterUser.getIsAdmin() == IS_ADMIN) { // führe Admin Client aus
			fuehreAdminClientAus();
		}else if(masterUser.getIsAdmin() == NOT_ADMIN) { // führe normalen Benutzerclient aus
			fuehreNormalenClientAus();
		}else {
			System.err.println("Fehler!");
			programmBeenden(1);
		}
	}
	
	private void fuehreNormalenClientAus() {
		new ClientDialog(new BenutzerClient(masterUser)).StartClientDialog();
	}
	
	private void fuehreAdminClientAus() {
		new AdminDialog(new AdminClient(masterUser)).StartAdminDialog();
	}

	/* ---Legacy---
	private void startOnlineCheck(){
		Timer timer = new Timer();
		timer.schedule(new OnlineStatus(masterUser), 0, 30000); //600000
		//timer.cancel();
	
	}*/
	
	public static void programmBeenden(int status) {
		System.out.println("Programm wird beendet...");
		OnlineasThread.interruptOnlineasThread(); //Beenden des HintergrundThreads (Ausgelagert)
		System.exit(status);
	}
	
	public static void main(String[] args) throws InterruptedException {
		new MasterController();
	}
}
