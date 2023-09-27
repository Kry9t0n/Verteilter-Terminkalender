package client.login;

import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import client.Benutzer;
import client.mastercontroller.MasterController;

/**
 * @author Alejandro Freyermuth
 *
 */
public class LoginDialog {
	private Benutzer masterUser;
	private Scanner scanner;

	public LoginDialog(Benutzer masterUser) {
		this.masterUser = masterUser;
		scanner = new Scanner(System.in);
		start();
	}

	private void start() {
		boolean loginSuccess;

		do {
			loginSuccess = true;
			try {
				dialogAusgeben();
				int eingabe = eingabeLesen();
				LoginDialogOptions option = LoginDialogOptions.getOptionByOrdinal(eingabe);
				optionAusfuehren(option);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				loginSuccess = false;
			}
		} while (!loginSuccess);
		System.out.println("Login erfolgreich...");
	}

	private void dialogAusgeben() {
		System.out.println("### LoginDialog ###");
		System.out.println("Verfügbare Optionen: ");
		for (LoginDialogOptions op : LoginDialogOptions.values()) {
			System.out.println(op.getInfo() + ": " + op.ordinal());
		}
	}

	private int eingabeLesen() {
		while(true) {
			System.out.println("Bitte Optionsauswahl eingeben: ");
			String rawInput = scanner.next();
			try {
				int inputToInt = Integer.valueOf(rawInput);
				return inputToInt;
			} catch (Exception e) {
				System.err.println("Fehler bei der Eingabe!");
			}
		}
	}

	private void optionAusfuehren(LoginDialogOptions option)
			throws JsonMappingException, JsonProcessingException, Exception {
		switch (option) {
		case LOGIN:
			fuehreLoginAus();
			break;
		case CANCEL:
			cancel();
			break;
		}
	}

	private void fuehreLoginAus() throws JsonMappingException, JsonProcessingException, Exception {

		// Werte von Benutzer einlesen
		System.out.println("### Login: ###");
		System.out.print("Bitte Username eingeben: ");
		String username = scanner.next();
		System.out.print("Bitte Passwort eingeben: ");
		String password = scanner.next();

		// LoginClient starten
		LoginClient l_client = new LoginClient(username, password, masterUser);
		l_client.login();
	}

	private void cancel() {
		MasterController.programmBeenden(0);
	}
	
	//Nur zum Testen!!!
	/*
	public static void main(String[] args) {
		Benutzer master = new Benutzer();
		LoginDialog ld = new LoginDialog(master);

		System.out.println(master);
	}*/

}
