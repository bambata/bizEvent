package com.opportunity.mainsite.client.view;

import com.google.gwt.user.client.ui.RootPanel;

public class MainView {

  /**
   * Start up the view
   */
  public void go(){

    ShopInformationFormView shopInfoForm = new ShopInformationFormViewImpl();
    RootPanel.get("shopForm").add(shopInfoForm.toWidget());

  }



}
