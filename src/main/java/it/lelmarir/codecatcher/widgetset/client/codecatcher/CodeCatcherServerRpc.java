package it.lelmarir.codecatcher.widgetset.client.codecatcher;

import com.vaadin.shared.communication.ServerRpc;

public interface CodeCatcherServerRpc extends ServerRpc {

	public void onCodeCatched(CodeCatchedEvent event);

}
