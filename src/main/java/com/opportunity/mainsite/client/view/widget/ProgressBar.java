package com.opportunity.mainsite.client.view.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class ProgressBar extends Widget {

	int width;

	int height;
	
	String color;

	Element barElement;

	int curProgress;
	
	public ProgressBar(){
		
	}

	public ProgressBar(int height, int width, int curProgress) {

		// Create the outer shell
		setElement(DOM.createDiv());
		DOM.setStyleAttribute(getElement(), "width", width + "px");
		DOM.setStyleAttribute(getElement(), "height", height + "px");
		DOM.setStyleAttribute(getElement(), "border-color", color);
		setStyleName("progressBar-Widget");

		// Create the bar element
		barElement = DOM.createDiv();
		DOM.appendChild(getElement(), barElement);
		DOM.setStyleAttribute(barElement, "border-bottom", height + "px");
		DOM.setStyleAttribute(barElement, "border-bottom-color", color);

		// Set the current progress
		setCurProgress(curProgress);
	}

	public void setBarStyleName(String barClassName) {
		DOM.setElementProperty(barElement, "className", barClassName);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Element getBarElement() {
		return barElement;
	}

	public void setBarElement(Element barElement) {
		this.barElement = barElement;
	}

	public int getCurProgress() {
		return curProgress;
	}

	public void setCurProgress(int curProgress) {
		this.curProgress = curProgress;
		DOM.setStyleAttribute(barElement, "width", curProgress + "%");
	}
	

}
