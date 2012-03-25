package com.opportunity.mainsite.client;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.opportunity.mainsite.client.presenter.Presenter;
import com.opportunity.mainsite.client.view.MainView;
import com.opportunity.mainsite.shared.ApplicationAutoBeanFactory;

public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private HandlerManager eventBus;

	private HasWidgets container;

	private MainView mainView;
	
	public static ApplicationAutoBeanFactory applicationAutoBeanFactory = GWT.create(ApplicationAutoBeanFactory.class);

	public AppController(HandlerManager eventBus) {

		this.eventBus = eventBus;
		
		History.addValueChangeHandler(this);

	}

	public void setEventBus(HandlerManager eventBus) {
		this.eventBus = eventBus;
	}

	public HandlerManager getEventBus() {
		return eventBus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	  if(event.getValue().equals(ApplicationGlobalState.shopRegistration.toString())){

	    if(mainView == null)
	      mainView = new MainView();

	    mainView.go();

	  }

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
