package it.lelmarir.codecatcher.widgetset;

import java.util.LinkedList;

import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatchedEvent;
import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatcherServerRpc;

import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.AbstractExtension;
import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatcherState;

public class CodeCatcher extends AbstractExtension {

	public interface CodeCatchedListener {
		void onCodeCatch(CodeCatchedEvent event);
	}
	
	private LinkedList<CodeCatchedListener> listeners = new LinkedList<CodeCatcher.CodeCatchedListener>();
	
	private CodeCatcherServerRpc rpc = new CodeCatcherServerRpc() {
		@Override
		public void onCodeCatched(CodeCatchedEvent event) {
			for(CodeCatchedListener listener : listeners) {
				listener.onCodeCatch(event);
			}
		}
	}; 
	
	public CodeCatcher(AbstractClientConnector connector) {
		registerRpc(rpc);
		extend(connector);
	}

	@Override
	public CodeCatcherState getState() {
		return (CodeCatcherState) super.getState();
	}
	
	public void addListener(CodeCatchedListener listener) {
		this.listeners.add(listener);
	}
	
	public boolean removeListener(CodeCatchedListener listener) {
		return listeners.remove(listener);
	}

	public char getStartCharacter() {
		return (char)getState().codeStartKeyCode;
	}

	public void setStartCharacter(char character) {
		getState().codeStartKeyCode = (int)character;
	}

	public char getEndCharacter() {
		return (char)getState().codeEndKeyCode;
	}

	public void setEndCharacter(char character) {
		getState().codeEndKeyCode = (int)character;
	}

	public int getMaxLenght() {
		return getState().codeMaxLenght;
	}

	public void setMaxLenght(int codeMaxLenght) {
		getState().codeMaxLenght = codeMaxLenght;
	}
	
}
