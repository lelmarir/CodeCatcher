package it.lelmarir.codecatcher.widgetset.client.codecatcher;

public class CodeCatchedEvent {
	private final String code;
	
	public CodeCatchedEvent(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
