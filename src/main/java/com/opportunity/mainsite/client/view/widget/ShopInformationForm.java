package com.opportunity.mainsite.client.view.widget;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public interface ShopInformationForm {
	
	interface ShopInformationFormObserver{
		
		public void onSubmitButtonClick(Map<String, String> formValues);
		
	}

	void setPresenter(ShopInformationFormObserver presenter);

	Widget toWidget();
	
}
