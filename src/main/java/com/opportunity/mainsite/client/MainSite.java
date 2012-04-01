package com.opportunity.mainsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class MainSite implements EntryPoint{
  
	
  @Override
  public void onModuleLoad() {
	 
	AppController appController = MainSiteGinjector.INSTANCE.getAppController();
	
    appController.go(RootPanel.get("shopForm"));

  }





}