# Verteilter-Terminkalender
Projekt Repository der Gruppe Zuse im Sommersemester 2023

## allg. wichtig
Darstellungszeitraum: 1 Woche

## Allgemeines REST-API Schema
![](https://github.com/Kry9t0n/Verteilter-Terminkalender/blob/a34d058803f7822215bccf0552e2f4d9350ae91c/res/pictures/REST%20API%20Schema%201.png)
![](https://github.com/Kry9t0n/Verteilter-Terminkalender/blob/a34d058803f7822215bccf0552e2f4d9350ae91c/res/pictures/REST%20API%20Schema%202.png)
![](https://github.com/Kry9t0n/Verteilter-Terminkalender/blob/a34d058803f7822215bccf0552e2f4d9350ae91c/res/pictures/REST%20API%20Schema%203.png)

## Hinzufügen eines anderen Users zu einem Termin (Schema)
![](https://github.com/Kry9t0n/Verteilter-Terminkalender/blob/b79365d660a4d4f97cbd12b10acfa16ef702ba69/res/pictures/UserZuTerminHinzufuegen.png)

### Datenbankabläufe (bzgl. des Hinzufügens eines anderen Users)
Lädt ein Client (C1) einen anderen Client (C2) zu einem seiner Termine ein, so muss er 
beim erstellen von einem Termin engeben welche User hinzugefügt werden (Benutzername). TerminID und BenutzerID's werden in eigener DB-Table abgespeichert (In dieser Table werden alle Einladungen erfasst).

## Benutzerauthentifikation
Übermittlung von Benutzername und Passwort. Der Server gleich dies mit den Einträgen in eigener DB-Table ab. 
Wenn der Benutzer authentifiziert ist (der Server also das Paar aus Benutzername und Passwort gefunden hat) wird eine Rückgabe gesendet, in der ein **boolean isAdmin**, welches auf der Clientseite ausgewertet werden muss, sowie die **BenutzerID** enthalten sind. Die Benutzerauthentifikation wird nur einmal beim Login durchgeführt. 

## Login/Logout
Login umfasst die Benutzerauthentifikation sowie ein POST-Request für den Onlinestatus.
Logout sendet ein DELETE-Request ebenfalls für den Onlinestatus.

## Onlinestatus
Es gibt eine eigene DB-Table sowie eine URL (REST API) die für die Erfassung des Onlinestatus da ist. 
Ein Einloggen einer Users sendet ein POST-Request an das Verzeichnis. Meldet er sich wieder ab, so wird ein DELETE-Request gesendet. 

Zusätzlich muss jeder Client in gewissen zeitlichen Intervallen (10 Minuten) eine Anfrage an den Server stellen, ob dieser noch online ist. Falls der Server online ist, antwortet er auf 
diese Anfrage mit einem gewissen Wert z.B. 1. Desweiteren kann der Server davon ausgehen, dass jeder Client der solche Anfragen stellt auch immernoch online ist. 
Clients bei denen zwei Mal das Intervall abgelaufen ist, in denen sie eine Anfrage an den Server stellen sollen, werden als offline angesehen und aus dem Verzeichnis/DB-Table entfernt.

## Admin-Client
Nur der Admin-Client kann neue Benutzer ins System einfügen!!!
Weitere Tätigkeiten:
 - Benutzer aus dem System entfernen
 - Termine löschen
 - Serverlaufzeit kontrollieren können
 - Anzahl der User im System einsehen können

Dafür soll der Admin-Client einen **EIGENEN** Admin-Dialog bekommen.

Hier eine simple, beispielhafte Darstellung wie man den Admin-Client schematisch realisieren könnte.
![](https://github.com/Kry9t0n/Verteilter-Terminkalender/blob/c2a2b583af6576f1ae98467017e2fb59746b2508/res/pictures/AdminClientVererbungsHierarchie.png)

# Konzept Server Grupppe
## Server
Tomcat

## Datenbank
eingebettete DB H2

## URLs
 - Termine
 - Benutzer
 - Admin anfragen
 - Online

## Ablauf
1. Login mit Benutzername und Passwort
     a. Bekommt die BentuerID und isAdmin zurück
2. Anfragen über JSON mit Benutzer ID als Authentification Header dann wird diese geprüft ob vorhanden
3. Bei Einladung wird die eingeladene Person über Benutzername hinzugefügt

## JSON
 - Termine
 - Benutzer
 - Admin Anfragen (Serverinfos)
 - Serveranfrage

## Datenbanken
Benutzertable:
 - int BenutzerID
 - varchar Benutzername
 - varchar Passwort
 - varchar Name
 - varchar Vorname
 - varchar isAdmin

TerminTable:
 - int TerminID
 - varchar Titel
 - date Datum
 - time Uhrzeit
 - int Dauer (in Minuten)
 - int ErstelllerID (Fremdschlüssel BenutzerID)
 - varchar Benutzername (eingeladene Person)

EingeladenTable:
 - int TerminID
 - int BenutzerID (aller eingeladener Benutzer)
 - int/bool istGesendet

Online Benutzer Table:
 - BenutzerID
 - letzte Zeit (Rückmeldung)

   
