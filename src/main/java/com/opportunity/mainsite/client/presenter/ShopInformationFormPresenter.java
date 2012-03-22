package com.opportunity.mainsite.client.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.opportunity.mainsite.client.view.ShopInformationFormView;
import com.opportunity.mainsite.client.view.ShopInformationFormViewImpl;

public class ShopInformationFormPresenter implements ShopInformationFormView.ShopInformationFormObserver, Presenter  {
	
	private ShopInformationFormView view;
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		view = new ShopInformationFormViewImpl();
		view.setPresenter(this);
		container.add(view.toWidget());
	}

	@Override
	public void onSubmitButtonClick() {
		// TODO Auto-generated method stub
		
	}

}
