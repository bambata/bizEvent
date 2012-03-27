package com.opportunity.mainsite.client.view;

import com.google.gwt.user.client.ui.RootPanel;
import com.opportunity.mainsite.client.presenter.ShopInformationFormPresenter;
import com.opportunity.mainsite.client.view.widget.ShopInformationForm;
import com.opportunity.mainsite.client.view.widget.ShopInformationFormWidget;

public class MainView {

  /**
   * Start up the view
   */
  public void go(){

    ShopInformationFormPresenter presenter = new ShopInformationFormPresenter();
    presenter.go(RootPanel.get("shopForm"));

  }



}
