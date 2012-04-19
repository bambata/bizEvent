package com.opportunity.mainsite.client.view.widget;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.user.client.ui.Widget;
import com.opportunity.mainsite.shared.Error;
import com.opportunity.mainsite.shared.ShopInformation;

public interface ShopInformationForm {

	interface ShopInformationFormObserver {

		public void onSubmitButtonClick(Map<String, String> formValues);

		public void onAddedFile();
		
		public void getAddressLocation(String humanReadableAddressSnipset);

	}

	void setPresenter(ShopInformationFormObserver presenter);

	void setViolations(Set<ConstraintViolation<ShopInformation>> violations);

	Widget toWidget();

	void setWidgetState(ShopInformationFormState state);

	void setWidgetState(ShopInformationFormState state, Error error);
	
	void updateMap(LatLng geolocation, LatLngBounds viewport, Map<String, String> addressComponent);

	enum ShopInformationFormState {
		success, error, standard
	}

}
