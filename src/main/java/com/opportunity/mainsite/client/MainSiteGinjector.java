package com.opportunity.mainsite.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.opportunity.mainsite.client.presenter.ShopInformationFormPresenter;

@GinModules(MainSiteModule.class)
public interface MainSiteGinjector extends Ginjector {
	
	public static final MainSiteGinjector INSTANCE = GWT.create(MainSiteGinjector.class);
	
	ShopInformationFormPresenter getShopInformationFormPresenter();
	
	AppController getAppController();
	
}
