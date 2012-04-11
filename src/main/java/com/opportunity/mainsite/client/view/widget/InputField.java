package com.opportunity.mainsite.client.view.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.opportunity.mainsite.client.MainSiteResources;
import com.opportunity.mainsite.client.css.SkinCSSResource;

public class InputField<T extends FocusWidget> extends FocusWidget {
	
	private T input;

	private String label;

	private String errorMessage;

	private Element firstColumn;

	private Element secondColumn;

	private Element row;
	
	private SkinCSSResource skin = MainSiteResources.INSTANCE.getSkinStyle();

	public InputField() {
		
		this(Document.get().createTableElement(), MainSiteResources.INSTANCE.getSkinStyle().formInputField());

	}
	
	public InputField(Element container, String styleName) {

		super(container);

		if (styleName != null)
			setStyleName(styleName);
		
		//set up the row but don't attach it
		row = DOM.createTR();

		firstColumn = DOM.createTD();
		firstColumn.setClassName(skin.formInputLabel());
		row.appendChild(firstColumn);

		secondColumn = DOM.createTD();
		secondColumn.setClassName(skin.formInputValue());
		row.appendChild(secondColumn);

	}
	
	@UiChild(tagname="input", limit=1)
	public void setInput(T input) {
		this.input = input;
		secondColumn.appendChild(input.getElement());
		
		if(!getElement().isOrHasChild(row))
			getElement().appendChild(row);
	}

	public T getInput() {
		return input;
	}

	public void setLabel(String label) {
		this.label = label;
		firstColumn.setInnerText(label + " :");
		
		if(!getElement().isOrHasChild(row))
			getElement().appendChild(row);
	}

	public String getLabel() {
		return label;
	}

	public void setErrorMessage(String errorMessage) {
		getElement().addClassName(skin.formInputError());
		this.errorMessage = errorMessage;
		
		Element divErrorMessage = DOM.createDiv();
		divErrorMessage.setClassName(skin.formInputErrorMessage());
		divErrorMessage.setInnerText(errorMessage);
		
		secondColumn.appendChild(divErrorMessage);
		
		if(!getElement().isOrHasChild(row))
			getElement().appendChild(row);
	}
	
	public void removeErrorMessage() {
		getElement().removeClassName(skin.formInputError());
		this.errorMessage = null;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void addInputFieldCustomClassName(String className){
		this.getElement().addClassName(className);
	}
	
	public void addLabelCustomClassName(String className){
		firstColumn.addClassName(className);
	}
	
	public void addValueCustomClassName(String className){
		secondColumn.addClassName(className);
	}
	
}
