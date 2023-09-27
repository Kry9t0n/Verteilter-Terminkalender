package client.Client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.Benutzer;
import client.Einladung_Rest;
import client.Termin;
import client.TerminRessoucen;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;

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
	private static final int EINLADUNGEN_ANNEHMEN = 6;
	private static final int EINLADUNGEN_ABLEHNEN = 7;
	private static final int ONLINE_BENUTZER_AUSGEBEN = 8;

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
		case 6:
			einladungenAnnehmen();
			break;
		case 7:
			einladungenAblehnen();
			break;
		case 8:
			listeOnlineBenutzerAuf();
			break;
		case 0:
			System.out.println("ClientDialog wurde beendet!");
			break;
		default:
			System.out.println("Eingabe falsch!");
		}
		
	}

	private void listeOnlineBenutzerAuf() {
		ArrayList<Benutzer> onlineListe = null;
		try {
			onlineListe = benutzerClient.fetchBenutzerOnlineListe();
		} catch (JsonProcessingException e) {
			System.out.println("Fehler beim fetchen der Onlineliste!");
		}
		
		if(onlineListe != null) {
			System.out.println("### Onlineliste ###");
			for(Benutzer b : onlineListe) {
				System.out.println(b.getBenutzerName() +" " + b.getBenutzerId());
			}
			System.out.println("------------------------------------------\n");
		}
		
	}
	
	private void einladungenAblehnen() {
		System.out.println("Welchen Einladung wollen sie ablehnen(Terminid:)");
		int terminid = leseGanzzahlEingabe();
		
		Benutzer benutzer;
		benutzer = benutzerClient.getBenutzer();
		
		Einladung_Rest.removeEinladung(client,terminid,benutzer.getBenutzerId());
		
		System.out.println("Einladung wuerde abgelehnt!\n");
	}

	private void einladungenAnnehmen() {
		System.out.println("Welchen Einladung wollen sie annhemen(Terminid:)");
		int terminid = leseGanzzahlEingabe();
		
		Benutzer benutzer;
		benutzer = benutzerClient.getBenutzer();
		
		Termin terminEinladung = TerminRessoucen.getEinzelTerminByID(client, terminid);
		terminEinladung.setIdErsteller(benutzer.getBenutzerId());
	
		TerminRessoucen.addTermin(client, terminEinladung);
		System.out.println("Einladung wuerde als termin hinzugefuegt!\n");
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
		
		String day = "";
		day = (date.getDayOfMonth() < 10) ? "0"+date.getDayOfMonth() : ""+date.getDayOfMonth();
		
		String month = "";
		month = (date.getMonthValue() < 10) ? "0"+date.getMonthValue() : ""+date.getMonthValue();
		
		
		//ArrayList<Termin> tagesListe = benutzerClient.fetchAlleTermineEinesTages(date);
		
		ArrayList<Termin> tagesListe = (ArrayList<Termin>) TerminRessoucen.getAlleTermineAnEinemTag(client, date, benutzerClient.getBenutzer());
		
		String ausgabe = "Termine am:"+ day + "," + month + "," + jahr + "\n";
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
		
		
		LocalDate date = LocalDate.of(jahr,monat,tag);
		
		String day = "";
		day = (date.getDayOfMonth() < 10) ? "0"+date.getDayOfMonth() : ""+date.getDayOfMonth();

		String month = "";
		month = (date.getMonthValue() < 10) ? "0"+date.getMonthValue() : ""+date.getMonthValue();
		
		String datum = day + "," + month + "," + date.getYear();
		
		updateTermin.setDatum(datum);
		
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
		//benutzerClient.getEinladungen();
		ArrayList<Termin> einladungsSpeicher = Einladung_Rest.getEinladungen(client, benutzerClient.getBenutzer().getBenutzerId());
		for(Termin t : einladungsSpeicher) {
			System.out.println(t);
		}
		System.out.println("------------------------------------------\n");
	}

	private void termineAusgeben() {
		benutzerClient.fetchTermineInDarstellungszeitraum();
		
		ArrayList<ArrayList<Termin>> alleTermine = benutzerClient.getTerminSpeicher();
		
		System.out.println("------------------------------------------\n");
		System.out.println("Ausgabe der Termine!");
		System.out.println("------------------------------------------\n");
		
		for(int index = 0; index < benutzerClient.getANZAHL_TERMIN_DARSTELLUNG(); index++) {
			System.out.println("Tag " + (index+1));
			ArrayList<Termin> tagesListe = alleTermine.get(index);
			if(tagesListe.isEmpty()) {
				System.out.println("Keine Termine");
			}else {
				for(Termin termin : tagesListe) {
					System.out.println(termin);
				}
			}
			System.out.println("------------------------------------------\n");
		}
		
		/*
		for (ArrayList<Termin> terminListe : alleTermine) {
		    for (Termin termin : terminListe) {
		        System.out.println(termin.toString());
		    }
			System.out.println("------------------------------------------\n");
		}*/
	
	}
		
	

	private void terminErstellen() {
		String einladungBenutzer;
		String eingabe2 = "";
		
		System.out.println("Titel:");
		String titel = input.next();
		
		System.out.println("Jahr:");
		int jahr = leseGanzzahlEingabe();
		System.out.println("Monat(In zahl (01 - 12)):");
		int monat = leseGanzzahlEingabe();
		System.out.println("Tag:");
		int tag = leseGanzzahlEingabe();
		
		LocalDate date = LocalDate.of(jahr,monat,tag); 
		
		String day = "";
		day = (date.getDayOfMonth() < 10) ? "0"+date.getDayOfMonth() : ""+date.getDayOfMonth();

		String month = "";
		month = (date.getMonthValue() < 10) ? "0"+date.getMonthValue() : ""+date.getMonthValue();
		
		String datum = day + "," + month + "," + date.getYear();
		
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
		
		Termin termin = new Termin(titel,datum,dauer,benutzer.getBenutzerId(),einladungBenutzer);
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
				+ EINLADUNGEN_ANNEHMEN + ":Einladungen annehmen\n"
				+ EINLADUNGEN_ABLEHNEN + ":Einladungen ablehnen\n"
				+ ONLINE_BENUTZER_AUSGEBEN + ": Gibt eine Liste aller Benutzer die online sind aus\n"
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
