package com.opportunity.mainsite.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opportunity.mainsite.client.presenter.Presenter;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private HandlerManager eventBus;

	private HasWidgets container;

	public AppController(HandlerManager eventBus) {

		this.eventBus = eventBus;

	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	}

	@Override
	public void go(HasWidgets container) {
		
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("shopRegistration");
		} else {
			History.fireCurrentHistoryState();
		}

	}

	public void setContainer(HasWidgets container) {
		this.container = container;
	}

	public HasWidgets getContainer() {
		return container;
	}

}
