package com.opportunity.mainsite.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class MainSite implements EntryPoint {
  
  @Override
  public void onModuleLoad() {

    loadMapApi();

  }

  private void loadMapApi() {

    boolean sensor = true;

    ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
    loadLibraries.add(LoadLibrary.ADSENSE);
    loadLibraries.add(LoadLibrary.DRAWING);
    loadLibraries.add(LoadLibrary.GEOMETRY);
    loadLibraries.add(LoadLibrary.PANORAMIO);
    loadLibraries.add(LoadLibrary.PLACES);

    Runnable onLoad = new Runnable() {
      public void run() {
        go();
      }
    };

    LoadApi.go(onLoad, loadLibraries, sensor);

  }

  private void go() {
    AppController appController = MainSiteGinjector.INSTANCE.getAppController();
    
    appController.go(RootPanel.get("shopForm"));
  }

}