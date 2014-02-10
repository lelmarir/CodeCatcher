package it.lelmarir.codecatcher.widgetset.client.codecatcher;

import java.util.logging.Logger;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.vaadin.shared.ui.Connect;

import it.lelmarir.codecatcher.widgetset.CodeCatcher;
import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatcherServerRpc;

import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.VConsole;

import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatcherState;

import com.vaadin.client.extensions.AbstractExtensionConnector;

@Connect(CodeCatcher.class)
public class CodeCatcherConnector extends AbstractExtensionConnector implements NativePreviewHandler {

	CodeCatcherServerRpc rpc = RpcProxy.create(CodeCatcherServerRpc.class, this);
	
	private int codeStartKeyCode;
	private int codeEndKeyCode;
	private int codeMaxLenght = 0;

	private boolean isReadingCode = false;
	private int codeLength = 0;
	private String code = "";
	
	public CodeCatcherConnector() {
		;
	}
	
	@Override
	protected void extend(ServerConnector target) {
		Event.addNativePreviewHandler(this);
	}
	
	public void onPreviewNativeEvent(NativePreviewEvent event) {
		if (event.getNativeEvent().getType().contains("key")) {
			int keyCode = -1;
			if (event.getNativeEvent().getType().equals("keypress")) {
				keyCode = event.getNativeEvent().getCharCode();
				if (this.isReadingCode == false && keyCode == codeStartKeyCode) {
					this.isReadingCode = true;
					this.code = "";
					this.codeLength = 0;
					event.cancel();
				} else if (this.isReadingCode == true) {
					if (keyCode == codeEndKeyCode) {
						this.isReadingCode = false;
						event.cancel();
						rpc.onCodeCatched(new CodeCatchedEvent(this.code));
					} else {
						if (this.codeMaxLenght == 0
								|| this.codeLength < this.codeMaxLenght) {
							event.cancel();
							this.code += (char) keyCode;
						} else {
							// errore, scarto il codice
							this.isReadingCode = false;
						}
					}
				}
			}
		}
	}

	@Override
	public CodeCatcherState getState() {
		return (CodeCatcherState) super.getState();
	}

	public int getCodeStartKeyCode() {
		return codeStartKeyCode;
	}

	public void setCodeStartKeyCode(int codeStartKeyCode) {
		VConsole.log("codeStartKeyCode = " + codeStartKeyCode);
		this.codeStartKeyCode = codeStartKeyCode;
	}

	public int getCodeEndKeyCode() {
		return codeEndKeyCode;
	}

	public void setCodeEndKeyCode(int codeEndKeyCode) {
		this.codeEndKeyCode = codeEndKeyCode;
	}

	public int getCodeMaxLenght() {
		return codeMaxLenght;
	}

	public void setCodeMaxLenght(int codeMaxLenght) {
		this.codeMaxLenght = codeMaxLenght;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}
	
}

