package it.lelmarir.codecatcher.widgetset.client.codecatcher;

import com.vaadin.shared.annotations.DelegateToWidget;

public class CodeCatcherState extends com.vaadin.shared.AbstractComponentState {

	@DelegateToWidget
	public int codeStartKeyCode;
	@DelegateToWidget
	public int codeEndKeyCode;
	@DelegateToWidget
	public int codeMaxLenght = 0;

}