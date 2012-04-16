package com.opportunity.mainsite.client.view.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.opportunity.mainsite.client.MainSiteResources;
import com.opportunity.mainsite.client.css.SkinCSSResource;

public class InputField extends Widget implements HasWidgets {

	private Widget input;

	private String label;

	private String errorMessage;

	private Element firstColumn;

	private Element secondColumn;

	private Element row;

	private List<Widget> listofWidget;

	@Inject
	static SkinCSSResource skin;

	static {

		skin = MainSiteResources.INSTANCE.getSkinStyle();
		skin.ensureInjected();

	}

	public InputField() {

		this(Document.get().createTableElement(), skin.formInputField());

	}

	public InputField(Element container, String styleName) {

		super();

		setElement(container);

		if (styleName != null)
			setStyleName(styleName);

		// set up the row but don't attach it
		row = DOM.createTR();

		firstColumn = DOM.createTD();
		firstColumn.setClassName(skin.formInputLabel());
		row.appendChild(firstColumn);

		secondColumn = DOM.createTD();
		secondColumn.setClassName(skin.formInputValue());
		row.appendChild(secondColumn);
		
	}

	public Widget getInput() {
		return input;
	}

	public void setLabel(String label) {
		this.label = label;
		firstColumn.setInnerText(label + " :");
		
		
		//physically attach the widget
		if (!getElement().isOrHasChild(row))
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

		if (!getElement().isOrHasChild(row))
			getElement().appendChild(row);
	}

	public void removeErrorMessage() {
		getElement().removeClassName(skin.formInputError());
		this.errorMessage = null;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void addInputFieldCustomClassName(String className) {
		this.getElement().addClassName(className);
	}

	public void addLabelCustomClassName(String className) {
		firstColumn.addClassName(className);
	}

	public void addValueCustomClassName(String className) {
		secondColumn.addClassName(className);
	}

	@Override
	public void add(Widget w) {
		input = w;

		secondColumn.appendChild(input.getElement());

		if (!getElement().isOrHasChild(row))
			getElement().appendChild(row);
	}

	@Override
	public void clear() {
		input = null;
	}

	@Override
	public Iterator<Widget> iterator() {

		if (listofWidget == null)
			listofWidget = new ArrayList<Widget>();

		listofWidget.add(input);
		return listofWidget.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		input = null;
		return input == null;
	}

}
