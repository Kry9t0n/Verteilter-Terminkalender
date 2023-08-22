# Verteilter-Terminkalender
Projekt Repository der Gruppe Zuse im Sommersemester 2023

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
Übermittlung von Benutzernahme und Passwort. Der Server gleich dies mit den Einträgen in eigener DB-Table ab. 

## Onlinestatus
Es gibt eine eigene DB-Table sowie eine URL (REST API) die für die Erfassung des Onlinestatus da ist. 
Ein Einloggen einer Users sendet ein POST-Request an das Verzeichnis. Meldet er sich wieder ab, so wird ein DELETE-Request gesendet. 

Zusätzlich muss jeder Client in gewissen zeitlichen Intervallen eine Anfrage an den Server stellen, ob dieser noch online ist. Falls der Server online ist, antwortet er auf 
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
