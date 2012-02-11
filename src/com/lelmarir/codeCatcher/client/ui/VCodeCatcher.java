package com.lelmarir.codeCatcher.client.ui;

import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Widget;

/**
 * Client side widget which communicates with the server. Messages from the
 * server are shown as HTML and mouse clicks are sent to the server.
 */
public class VCodeCatcher extends Widget implements Paintable,
		Event.NativePreviewHandler {

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-codecatcher";

	public static final String CODE_READED_EVENT_IDENTIFIER = "code";

	/** The client side widget identifier */
	protected String paintableId;

	/** Reference to the server connection object. */
	protected ApplicationConnection client;

	private String code = "";
	private int codeStartKeyCode;
	private int codeEndKeyCode;
	private int codeMaxLenght = 0;
	private boolean isReadingCode = false;
	private int codeLength = 0;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VCodeCatcher() {
		// TODO This example code is extending the GWT Widget class so it must
		// set a root element.
		// Change to a proper element or remove this line if extending another
		// widget.
		setElement(Document.get().createDivElement());

		// This method call of the Paintable interface sets the component
		// style name in DOM tree
		setStyleName(CLASSNAME);

		Event.addNativePreviewHandler(this);
	}

	/**
	 * Called whenever an update is received from the server
	 */
	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
		// This call should be made first.
		// It handles sizes, captions, tooltips, etc. automatically.
		if (client.updateComponent(this, uidl, true)) {
			// If client.updateComponent returns true there has been no changes
			// and we
			// do not need to update anything.
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the client side identifier (paintable id) for the widget
		paintableId = uidl.getId();

		// Process attributes/variables from the server
		// The attribute names are the same as we used in
		// paintContent on the server-side
		this.codeStartKeyCode = uidl.getIntAttribute("codeStartKeyCode");
		this.codeEndKeyCode = uidl.getIntAttribute("codeEndKeyCode");
		this.codeMaxLenght = uidl.getIntAttribute("codeMaxLenght");
		
//		String code = uidl.getStringAttribute("code");
//		getElement().setInnerHTML("Code readed: " + code);
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
						client.updateVariable(paintableId,
								CODE_READED_EVENT_IDENTIFIER, this.code, true);
					} else {
						if (this.codeMaxLenght == 0
								|| this.codeLength < this.codeMaxLenght) {
							event.cancel();
							this.code += (char)keyCode;
						} else {
							// errore, scarto il codice
							this.isReadingCode = false;
						}
					}
				}
			}

//			client.updateVariable(paintableId, CODE_READED_EVENT_IDENTIFIER,
//					"d: " + "(" + keyCode + ", "
//							+ event.getNativeEvent().getType() + ")" + code,
//					true);
		}
	}
}
