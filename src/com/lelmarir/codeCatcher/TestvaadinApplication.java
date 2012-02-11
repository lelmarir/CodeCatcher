package com.lelmarir.codeCatcher;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class TestvaadinApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Testvaadin Application");
		final Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		TextField txt = new TextField();
		mainWindow.addComponent(txt);
		CodeCatcher c = new CodeCatcher();
		c.addEventListener(new CodeReadedHandler() {
			
			public void codeReaded(String code) {
				label.setValue(code);
			}
		});
		mainWindow.addComponent(c);
		setMainWindow(mainWindow);
	}

}
