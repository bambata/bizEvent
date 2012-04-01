package com.opportunity.mainsite.client;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.opportunity.mainsite.client.presenter.Presenter;
import com.opportunity.mainsite.client.presenter.ShopInformationFormPresenter;

@Singleton
public class AppController implements Presenter, ValueChangeHandler<String> {
	
	private EventBus eventBus;

	private HasWidgets container;

	private ShopInformationFormPresenter shopInformationFormPresenter;
	
	@Inject
	public AppController(EventBus eventBus) {

		this.eventBus = eventBus;
		
		History.addValueChangeHandler(this);

	}

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {

	  if(event.getValue().equals(ApplicationGlobalState.shopRegistration.toString())){
		  
		  shopInformationFormPresenter = MainSiteGinjector.INSTANCE.getShopInformationFormPresenter();
		  
		  shopInformationFormPresenter.go(container);
		  
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
