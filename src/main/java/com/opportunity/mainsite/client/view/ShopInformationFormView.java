package com.opportunity.mainsite.client.view;

import com.google.gwt.user.client.ui.Widget;

public interface ShopInformationFormView {
	
	interface ShopInformationFormObserver{
		
		public void onSubmitButtonClick();
		
	}

	void setPresenter(ShopInformationFormObserver presenter);

	Widget toWidget();
	
}
