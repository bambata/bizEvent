package com.opportunity.mainsite.client.view.widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.opportunity.mainsite.client.MainSiteResources;
import com.opportunity.mainsite.client.css.SkinCSSResource;

public class InputField extends Composite implements HasWidgets {

	private static final String INPUT_FIELD_HTML_STRUCTURE = "<table><tr><td><span id='label'></span></td><td><span id='input'></span></td></tr></table>";

	private HTMLPanel container = new HTMLPanel(INPUT_FIELD_HTML_STRUCTURE);

	private Label label;

	private Widget input;

	private Label errorMessage;
	
	private String styleName;
	
	private List<Widget> listOfComposedWidgets = new ArrayList<Widget>(2);

	@Inject
	static SkinCSSResource skin;

	static {

		skin = MainSiteResources.INSTANCE.getSkinStyle();
		skin.ensureInjected();

	}

	public InputField() {

		container.setStyleName(skin.formInputField(), true);

		initWidget(container);

	}

	public InputField(String label, String styleName) {

		super();

		container.setStyleName(skin.formInputField(), true);

		if (styleName != null)
			container.setStyleName(styleName, true);

		initWidget(container);

	}

	public Widget getInput() {
		return input;
	}

	public HTMLPanel getContainer() {
		return container;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {

		if (this.label == null) {
			this.label = label;
			this.label.setStyleName(skin.formInputLabel(), true);

			container.addAndReplaceElement(label, "label");

			if (listOfComposedWidgets.isEmpty())
				listOfComposedWidgets.add(label);
			else
				listOfComposedWidgets.set(0, label);
		}

	}

	public Label getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(Label errorMessage) {
		this.errorMessage = errorMessage;
	}

	public static SkinCSSResource getSkin() {
		return skin;
	}

	public static void setSkin(SkinCSSResource skin) {
		InputField.skin = skin;
	}

	public void setInput(Widget input) {
		
		if (this.input == null) {
			this.input = input;
			input.setStyleName(skin.formInputValue(), true);
			container.addAndReplaceElement(input, "input");

			if (listOfComposedWidgets.isEmpty())
				listOfComposedWidgets.add(new Label("Label"));
			else if (listOfComposedWidgets.size() == 1)
				listOfComposedWidgets.add(input);
			else if (listOfComposedWidgets.size() > 1)
				listOfComposedWidgets.set(1, input);
		}
	}

	@Override
	public void add(Widget w) {
		if (w instanceof Label)
			setLabel((Label) w);
		else
			setInput(w);
	}

	@Override
	public void clear() {
		listOfComposedWidgets = new ArrayList<Widget>();
		input = null;
		label = null;
		container = new HTMLPanel(INPUT_FIELD_HTML_STRUCTURE);
	}

	@Override
	public Iterator<Widget> iterator() {
		return listOfComposedWidgets.iterator();
	}

	@Override
	public boolean remove(Widget w) {
		if (w == input) {
			input = null;
			return listOfComposedWidgets.remove(w);
		}
		return false;
	}
	
	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
		container.setStyleName(styleName, true);
	}

}
