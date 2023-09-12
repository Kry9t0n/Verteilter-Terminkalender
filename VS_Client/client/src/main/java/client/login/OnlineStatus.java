

/**
 * Beim Ausloggen soll der Client sich beim Server abmelden (aufruf der Methode bei Logout), dadurch
 * wird der Client automatisch aus der Tabelle gelöscht (nicht mehr online)
 * -> zum abmelden DELETE-Request
 */

/**
 * 10 Minuten Intervall an Server um dessen Status abzufragen -> Bist du noch da?
 * Server antwortet mit Value 1 -> muss ausgewertet werden
 * Außerdem geht dann der Server davon aus, dass der Client ebenfalls noch online ist
 * Server löscht CLient aus online User, wenn intervall zweimal verpasst
 */


