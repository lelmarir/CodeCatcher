package com.lelmarir.codeCatcher;

import java.util.ArrayList;
import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * Server side component for the VCodeCatcher widget.
 */
@com.vaadin.ui.ClientWidget(com.lelmarir.codeCatcher.client.ui.VCodeCatcher.class)
public class CodeCatcher extends AbstractComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char codeStartKeyCode = (int)'ò';
	private char codeEndKeyCode = (int)'ò';
	private int codeMaxLenght = 0;
	
	private String code = "";
	
	private ArrayList<CodeReadedHandler> handlers;

	@Override
	public void paintContent(PaintTarget target) throws PaintException {
		super.paintContent(target);

		// Paint any component specific content by setting attributes
		// These attributes can be read in updateFromUIDL in the widget.
		target.addAttribute("code", code);
		target.addAttribute("codeStartKeyCode", codeStartKeyCode);
		target.addAttribute("codeEndKeyCode", codeEndKeyCode);
		target.addAttribute("codeMaxLenght", codeMaxLenght);
		
		// We could also set variables in which values can be returned
		// but declaring variables here is not required
	}

	/**
	 * Receive and handle events and other variable changes from the client.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void changeVariables(Object source, Map<String, Object> variables) {
		super.changeVariables(source, variables);

		if (variables.containsKey("code")) {
			this.code = variables.get("code").toString();

//			System.out.println(this.code);
			
			if(this.handlers != null){
				for (CodeReadedHandler h : this.handlers) {
					h.codeReaded(this.code);
				}
			}
			
			//requestRepaint();
		}
	}
	
	public void addEventListener(CodeReadedHandler handler){
		if(this.handlers == null)
			this.handlers = new ArrayList<CodeReadedHandler>(0);
		
		this.handlers.add(handler);
	}

	public char getCodeStartKeyCode() {
		return codeStartKeyCode;
	}

	public void setCodeStartKeyCode(char codeStartKeyCode) {
		this.codeStartKeyCode = codeStartKeyCode;
	}

	public char getCodeEndKeyCode() {
		return codeEndKeyCode;
	}

	public void setCodeEndKeyCode(char codeEndKeyCode) {
		this.codeEndKeyCode = codeEndKeyCode;
	}

	public int getCodeMaxLenght() {
		return codeMaxLenght;
	}

	public void setCodeMaxLenght(int codeMaxLenght) {
		this.codeMaxLenght = codeMaxLenght;
	}

	public String getReadedCode() {
		return code;
	}

}
