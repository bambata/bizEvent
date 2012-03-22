package com.opportunity.mainsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.opportunity.mainsite.client.view.MainView;

public class MainSite implements EntryPoint{

  @Override
  public void onModuleLoad() {

    new MainView().go();

  }





}