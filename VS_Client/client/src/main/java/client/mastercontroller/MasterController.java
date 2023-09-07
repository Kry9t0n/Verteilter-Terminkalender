package client.mastercontroller;

import client.Benutzer;
import client.login.LoginDialog;

public class MasterController {
	private Benutzer masterUser;
	
	public MasterController() {
		masterUser = new Benutzer();
		run();
	}
	
	private void run() {
		fuehreLoginClientAus();
		selectAndRunClient();
	}
	
	private void fuehreLoginClientAus() {
		LoginDialog ld = new LoginDialog(masterUser);
	}
	
	private void selectAndRunClient() {
		if(masterUser.getIsAdmin() == 1) { // führe Admin Client aus
			fuehreAdminClientAus();
		}else if(masterUser.getIsAdmin() == 0) { // führe normalen Benutzerclient aus
			fuehreNormalenClientAus();
		}else {
			System.err.println("Fehler! Programm wird beendet...");
			return;
		}
	}
	
	private void fuehreNormalenClientAus() {
		
	}
	
	private void fuehreAdminClientAus() {
		
	}
	
	public static void programmBeenden() {
		System.out.println("Programm wird beendet...");
		return;
	}
	
	public static void main(String[] args) {
		new MasterController();
	}
}
