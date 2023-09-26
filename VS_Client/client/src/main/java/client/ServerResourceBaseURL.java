package client;

/**
 * @author Alejandro Freyermuth
 * Enum das die Basisurl's aller Server Ressourcen auflistet.
 */

public enum ServerResourceBaseURL {
	LOGIN_BASE_URL("http://localhost:8080/VS_Server/webapi/login"),
	BENUTZER_BASE_URL("http://localhost:8080/VS_Server/webapi/benutzer"),
	ONLINE_BASE_URL("http://localhost:8080/VS_Server/webapi/online"),
	TERMIN_BASE_URL("http://localhost:8080/VS_Server/webapi/termin"),
	MONITORING_BASE_URL("http://localhost:8080/VS_Server/webapi/monitoring"),
	EINGELADEN_BASE_URL("http://localhost:8080/VS_Server/webapi/eingeladen");
	
	private final String URL;
	
	private ServerResourceBaseURL(String URL) {
		this.URL = URL;
	}

	public String getURL() {
		return URL;
	}
	
	
}
