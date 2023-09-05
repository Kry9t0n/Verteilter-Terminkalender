package client.login;

import java.util.Scanner;

import client.Benutzer;
import client.mastercontroller.MasterController;

public class LoginDialog {
	private Benutzer masterUser;
	private LoginClient login;
	private Scanner scanner;
	
	public LoginDialog(Benutzer masterUser) {
		this.masterUser = masterUser;
		scanner = new Scanner(System.in);
	}
	
	private void start() {
		dialogAusgeben();
		int eingabe = eingabeLesen();
		LoginDialogOptions option = LoginDialogOptions.getOptionByOrdinal(eingabe);
		optionAusfuehren(option);
	}
	
	private void dialogAusgeben() {
		System.out.println("### LoginDialog ###");
		for(LoginDialogOptions op : LoginDialogOptions.values()) {
			System.out.println(op.getInfo() + ": " + op.ordinal());
		}
	}
	
	private int eingabeLesen() {
		System.out.print("Bitte Optionsauswahl eingeben: ");
		return scanner.nextInt();
	}
	
	private void optionAusfuehren(LoginDialogOptions option) {
		switch (option) {
		case LOGIN:
			fuehreLoginAus();
			break;
		case CANCEL:
			cancel();
			break;
		}
	}
	
	private void fuehreLoginAus() {
		
	}
	
	
	private void cancel() {
		MasterController.programmBeenden();
	}
	
}
