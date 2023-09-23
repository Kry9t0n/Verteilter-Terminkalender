package client.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.Termin;
import client.TerminRessoucen;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;

/*
 * @author Alexei Brandt
 */
public class ClientDialog {
	private Scanner input;
	private Client client;
	private BenutzerClient benutzerClient;
	
	private static final int ENDE = 0;
	private static final int TERMIN_ERSTELLEN = 1;
	private static final int TERMINE_AUSGEBEN = 2;
	private static final int EINLADUNGEN_AUSGEBEN = 3;
	private static final int Termin_Bearbeiten = 4;
	private static final int TERMIN_MIT_BESTIMMTEN_DATUM = 5;
	
	public ClientDialog(BenutzerClient benutzerClient) {
		this.benutzerClient = benutzerClient;
		this.client = benutzerClient.getClient();
		input = new Scanner(System.in);
		
	}

	public void StartClientDialog() {
		System.out.print("Der Dialog für den Client wird ausgefürt\n");
		System.out.print("------------------------------------------\n");
		int eingabe = 0;
		
		do {
			try {
				DialogAusgeben();
				eingabe = eingebeLesen();
				eingabeAusfuehren(eingabe);
			} catch(IllegalArgumentException e) {
				System.out.println(e);
			} catch(InputMismatchException e) {
				System.out.println(e);
				input.nextLine();
			} catch(Exception e) {
				System.out.println(e);
				e.printStackTrace(System.out);
			}
		} while(eingabe != ENDE);
		
	}

	private void eingabeAusfuehren(int eingabe) {
		
		
		switch(eingabe){
		case 1: 
			terminErstellen();
			break;
		case 2:
			termineAusgeben();
			break;
		case 3:
			einladungenAusgeben();
			break;
		case 4:
			terminbearbeiten();
			break;
		case 5:
			termin_mit_bestimmten_datum();
			break;
		case 0:
			System.out.println("ClientDialog wurde beendet!");
			break;
		default:
			System.out.println("Eingabe falsch!");
		}
		
	}

	private void termin_mit_bestimmten_datum() {
		System.out.println("Geben Sie bitte das Datum an, um die Termine zuerhalten\n");
		
		int jahr;
		System.out.println("Jahr:");
		jahr = leseGanzzahlEingabe();
		
		int monat;
		System.out.println("Monat:");
		monat = leseGanzzahlEingabe();
		
		int tag;
		System.out.println("tag:");
		tag = leseGanzzahlEingabe();
		
		LocalDate date = LocalDate.of(jahr,monat,tag); 
		
		ArrayList<Termin> tagesListe = benutzerClient.fetchAlleTermineEinesTages(date);
		
		String ausgabe = "Termine am:"+ jahr + monat + tag + "\n";
		for (Termin termin: tagesListe) {
			ausgabe = ausgabe + termin.toString() + "\n";
		}
		
		System.out.println(ausgabe.toString());
	
	}

	private void terminbearbeiten() {
		String titel;
		int jahr;
		int monat;
		int tag;
		int stunde;
		int minute;
		int dauer;
		System.out.println("------------------------------------------\n");
		System.out.println("Welchen Termin wollen sie bearbeiten(Terminid:)");
		int terminid = leseGanzzahlEingabe();
		
		//termin wird mittels id geholt
		//termin toString
		Termin updateTermin = TerminRessoucen.getEinzelTerminByID(client, terminid); //TODO: prüfen ob das so passt
		
		//System.out.println("Welcher Parameter soll veraendert werden?");
		//System.out.println("------------------------------------------\n");
		System.out.println("Geben Sie die neuen Parameter an?");
		System.out.println("------------------------------------------\n");
		
		System.out.println("Neuer Titel:");
		titel = input.next();
		updateTermin.setTitel(titel);
		
		System.out.println("Neues Jahr:");
		jahr = leseGanzzahlEingabe();
		
		System.out.println("Neuer Monat:");
		monat = leseGanzzahlEingabe();
		
		System.out.println("Neuer Tag:");
		tag = leseGanzzahlEingabe();
		
		System.out.println("Neue Stunde:");
		stunde = leseGanzzahlEingabe();
		
		System.out.println("Neue Minuten:");
		minute = leseGanzzahlEingabe();
		
		LocalDateTime date = LocalDateTime.of(jahr,monat,tag,stunde,minute); 
		String datum = date.toString();
		updateTermin.setDatum(date);
		
		System.out.println("Neue Dauer:");
		dauer = leseGanzzahlEingabe();
		updateTermin.setDauer(dauer);
		/*
		int eingabe;
		do {
			System.out.print("1:Titel\n"
				+ "2:Jahr\n"
				+ "3:Monat\n"
				+ "4:Tag\n"
				+ "5:Stunde\n"
				+ "6:Minute\n"
				+ "7:Dauer\n"
				+ ENDE + ":Ende\n");
		
			eingabe = input.nextInt();
			
			switch(eingabe){
			case 1: 
				System.out.println("Neuer Titel:");
				titel = input.next();
				updateTermin.setTitel(titel);
				break;
			case 2:
				System.out.println("Neues Jahr:");
				jahr = leseGanzzahlEingabe();
				break;
			case 3:
				System.out.println("Neuer Monat:");
				monat = leseGanzzahlEingabe();
				break;
			case 4:
				System.out.println("Neuer Tag:");
				tag = leseGanzzahlEingabe();
				break;
			case 5:
				System.out.println("Neue Stunde:");
				stunde = leseGanzzahlEingabe();
				break;
			case 6:
				System.out.println("Neue Minuten:");
				minute = leseGanzzahlEingabe();
				break;
			case 7:
				System.out.println("Neue Dauer:");
				dauer = leseGanzzahlEingabe();
				updateTermin.setDauer(dauer);
				break;
			case 0:
				break;
			default:
				System.out.println("Eingabe falsch!");
		}
	} while(eingabe != 0);*/
		//TerminRessoucen terminressource;
		TerminRessoucen.updateTermin(client, updateTermin);
		System.out.println("Termin wuerde bearbeitet!");
	
	}


	private void einladungenAusgeben() {
		System.out.println("------------------------------------------\n");
		System.out.println("Ausgabe der Einladungen!");
		System.out.println("------------------------------------------\n");
	}

	private void termineAusgeben() {
		benutzerClient.fetchTermineInDarstellungszeitraum();
		
		ArrayList<ArrayList<Termin>> alleTermine = benutzerClient.getTerminSpeicher();
		
		System.out.println("------------------------------------------\n");
		System.out.println("Ausgabe der Termine!");
		System.out.println("------------------------------------------\n");
		
		for (ArrayList<Termin> terminListe : alleTermine) {
		    for (Termin termin : terminListe) {
		        System.out.println(termin.toString());
		    }
			System.out.println("------------------------------------------\n");
		}
	
	}
		
	

	private void terminErstellen() {
		String einladungBenutzer;
		String eingabe2 = "";
		
		System.out.println("Titel:");
		String titel = input.next();
		
		System.out.println("Jahr:");
		int jahr = leseGanzzahlEingabe();
		System.out.println("Monat(In zahl z.b 10):");
		int monat = leseGanzzahlEingabe();
		System.out.println("Tag:");
		int tag = leseGanzzahlEingabe();
		System.out.println("Stunde:");
		int stunde = leseGanzzahlEingabe();
		System.out.println("Minuten:");
		int minuten = leseGanzzahlEingabe();
		
		LocalDateTime date = LocalDateTime.of(jahr,monat,tag,stunde,minuten); 
		String datum = date.toString();
		
		System.out.println("Dauer des Termins:");
		int dauer = leseGanzzahlEingabe();
		
		System.out.println("Wollen sie einen Benutzer zum Termin Einladen, dann Ja eingeben");
		String eingabe = input.next();
		
		if(eingabe.equals("Ja") || eingabe.equals("ja"))
		{
			System.out.println("Benutzername:");
			einladungBenutzer = input.next();
			while(!(eingabe2.equals("Nein")) && !(eingabe2.equals("nein"))) {
				System.out.println("Wollen sie noch einen Benutzer zum Termin Einladen?(falls nicht Nein eingeben)");
				eingabe2 = input.next();
				if(!(eingabe2.equals("Nein")) && !(eingabe2.equals("nein"))) {
					einladungBenutzer = einladungBenutzer+","+eingabe2;
				}
			}				
		}
		else {
			einladungBenutzer = "";	
		}
		
		Benutzer benutzer;
		benutzer = benutzerClient.getBenutzer();
		
		Termin termin = new Termin(titel,date,dauer,benutzer.getBenutzerId(),einladungBenutzer);
		TerminRessoucen.addTermin(client, termin);
		
		System.out.println("Termin wuerde erstellt !!!");
		//System.out.println(termin);
		
	}

	private int eingebeLesen() {
	    while (!input.hasNextInt()) {
	    	throw new InputMismatchException("Nur Interger eingeben, Keine Strings!");
	        
	    }
	    
	    int eingabe = input.nextInt();
	    return eingabe;
	}

	private void DialogAusgeben() {
		System.out.print(TERMIN_ERSTELLEN + ":Termin erstellen\n"
				+ TERMINE_AUSGEBEN + ":Termin ausgeben\n"
				+ EINLADUNGEN_AUSGEBEN + ":Einladungen ausgeben\n"
				+ Termin_Bearbeiten + ":Termin bearbeiten\n"
				+ TERMIN_MIT_BESTIMMTEN_DATUM + ":Termin mit bestimmten Datum ausgeben\n"
				+ ENDE + ":Ende\n");
	}
	
	private int leseGanzzahlEingabe() {
	        if (input.hasNextInt()) {
	            int zahl = input.nextInt();
	            input.nextLine(); // Um den Zeilenumbruch zu verarbeiten
	            return zahl;
	        } else {
	        	throw new InputMismatchException("Nur Interger eingeben, Keine Strings!");
	        }
	    
	}	
	
	/* NUR ZUM TESTEN!!!
	public static void main(String[] args) {
		
		ClientDialog dialog = new ClientDialog(ClientBuilder.newClient());
		dialog.StartClientDialog();
	}*/	
	

}