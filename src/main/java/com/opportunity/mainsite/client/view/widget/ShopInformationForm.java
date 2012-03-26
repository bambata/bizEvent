package com.opportunity.mainsite.client.view.widget;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.user.client.ui.Widget;
import com.opportunity.mainsite.shared.ErrorIF;
import com.opportunity.mainsite.shared.ShopInformationIF;

public interface ShopInformationForm {

	interface ShopInformationFormObserver{

		public void onSubmitButtonClick(Map<String, String> formValues);

		public void onAddedFile();

	}

	void setPresenter(ShopInformationFormObserver presenter);

	void setViolations(Set<ConstraintViolation<ShopInformationIF>> violations);

	Widget toWidget();

  void setWidgetState(ShopInformationFormState state);

  void setWidgetState(ShopInformationFormState state, ErrorIF error);

  enum ShopInformationFormState {
    success,
    error,
    standard
  }

}
