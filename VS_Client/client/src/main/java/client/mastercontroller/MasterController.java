package client.mastercontroller;

import java.util.Timer;

import client.Benutzer;
import client.OnlineStatus;
import client.Client.BenutzerClient;
import client.Client.ClientDialog;
import client.login.LoginDialog;
import java.util.Timer;

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
	private Benutzer masterUser;
	
	public MasterController() {
		masterUser = new Benutzer();
		run();
	}
	
	private void run() {
		fuehreLoginClientAus();
		startOnlineCheck();
		selectAndRunClient();
	}
	
	private void fuehreLoginClientAus() {
		LoginDialog ld = new LoginDialog(masterUser);
	}
	
	private void selectAndRunClient() {
		if(masterUser.getIsAdmin() == 1) { // führe Admin Client aus
			fuehreAdminClientAus();
		}else if(masterUser.getIsAdmin() == -1) { // führe normalen Benutzerclient aus
			fuehreNormalenClientAus();
		}else {
			System.err.println("Fehler!");
			programmBeenden();
		}
	}
	
	private void fuehreNormalenClientAus() {
		new ClientDialog(new BenutzerClient(masterUser)).StartClientDialog();
	}
	
	private void fuehreAdminClientAus() {
		
	}

	private void startOnlineCheck(){
		Timer timer = new Timer();
		timer.schedule(new OnlineStatus(), 0, 600);
	}
	
	public static void programmBeenden() {
		System.out.println("Programm wird beendet...");
		return;
	}
	
	public static void main(String[] args) {
		new MasterController();
	}
}
