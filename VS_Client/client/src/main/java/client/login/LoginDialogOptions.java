package client.login;

public enum LoginDialogOptions {
	
	LOGIN("Systemlogin: Benutzername und Passwort benötigt."),
	CANCEL("Abbruch. Programm wird beendet.");
	
	private String info;
	
	private LoginDialogOptions(String info) {
		this.info = info;
	}
	
	public static LoginDialogOptions getOptionByOrdinal(int ordinal) {
		
		for(LoginDialogOptions op : LoginDialogOptions.values()) {
			if(op.ordinal() == ordinal) {
				return op;
			}
		}
		
		throw new IllegalArgumentException("Keine Valide Option gewählt");
	}
	
	public boolean cancelGewaehlt() {
		if(this.ordinal() == CANCEL.ordinal()) {
			return true;
		}
		
		return false;
	}
	
	public String getInfo() {
		return this.info;
	}
	
}
