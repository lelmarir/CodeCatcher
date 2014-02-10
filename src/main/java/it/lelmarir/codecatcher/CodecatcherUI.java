package it.lelmarir.codecatcher;

import it.lelmarir.codecatcher.widgetset.CodeCatcher;
import it.lelmarir.codecatcher.widgetset.CodeCatcher.CodeCatchedListener;
import it.lelmarir.codecatcher.widgetset.client.codecatcher.CodeCatchedEvent;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("codecatcher")
@Widgetset("it.lelmarir.codecatcher.widgetset.CodecatcherWidgetset")
public class CodecatcherUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = CodecatcherUI.class, widgetset = "it.lelmarir.codecatcher.CodecatcherWidgetset")
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		final TextField text = new TextField();
		layout.addComponent(text);
		
		CodeCatcher catcher = new CodeCatcher(layout);
		catcher.setStartCharacter('1');
		catcher.setStartCharacter('0');
		catcher.addListener(new CodeCatchedListener() {			
			@Override
			public void onCodeCatch(CodeCatchedEvent event) {
				text.setValue(event.getCode());
			}
		});
	}

}