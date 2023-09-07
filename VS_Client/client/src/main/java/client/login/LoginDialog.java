package client.login;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import client.Benutzer;
import client.mastercontroller.MasterController;

public class LoginDialog {
	private Benutzer masterUser;
	private LoginClient login;
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
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				loginSuccess = false;
			} catch (InputMismatchException e) {
				System.out.println("Fehler! Achtung: NUR Zahlen eingeben!");
				loginSuccess = false;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				loginSuccess = false;
			}
		} while (!loginSuccess);

	}

	private void dialogAusgeben() {
		System.out.println("### LoginDialog ###");
		System.out.println("Verfügbare Optionen: ");
		for (LoginDialogOptions op : LoginDialogOptions.values()) {
			System.out.println(op.getInfo() + ": " + op.ordinal());
		}
	}

	private int eingabeLesen() {
		int choice = 0;
		boolean input_accepted = true;

		do {
			System.out.println("Bitte Optionsauswahl eingeben: ");
			if (!scanner.hasNextInt()) {

				input_accepted = false;
				System.out.println("Es werden nur Zahlen akzeptiert. Eingabe bitte wiederholen!");

				throw new InputMismatchException();

			} else {
				choice = scanner.nextInt();
			}
		} while (!input_accepted);

		return choice;
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
		MasterController.programmBeenden();
	}
	
	//Nur zum Testen!!!
	/*
	public static void main(String[] args) {
		Benutzer master = new Benutzer();
		LoginDialog ld = new LoginDialog(master);

		System.out.println(master);
	}*/

}
