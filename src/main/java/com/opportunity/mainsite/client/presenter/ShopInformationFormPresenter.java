package com.opportunity.mainsite.client.presenter;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.opportunity.mainsite.client.AppController;
import com.opportunity.mainsite.client.view.widget.ShopInformationForm;
import com.opportunity.mainsite.client.view.widget.ShopInformationFormWidget;
import com.opportunity.mainsite.shared.ShopInformation;
import com.opportunity.mainsite.shared.ShopInformationIF;

public class ShopInformationFormPresenter implements ShopInformationForm.ShopInformationFormObserver, Presenter  {
	
	private static final String SHOP_MANAGEMENT_SHOP_PERSISTER = GWT.getModuleBaseURL() + "shopManagementService/shop/create";
	private static final String FILE_SERVICE_CALLBACK = GWT.getModuleBaseURL() + "fileService/callbackUrl";
	
	private ShopInformationForm view;
	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		view = new ShopInformationFormWidget();
		view.setPresenter(this);
		container.add(view.toWidget());
	}

	@Override
	public void onSubmitButtonClick(Map<String, String> values) {
		
		AutoBean<ShopInformationIF> autoBean = AppController.applicationAutoBeanFactory.shopInformation();
		
		//create the bean
		ShopInformationIF info = autoBean.as();
		info.setCountry(values.get("country"));
		info.setDescription(values.get("description"));
		info.setNumber(Integer.parseInt(values.get("number")));
		info.setStreet(values.get("street"));
		info.setShopName(values.get("shopName"));
		info.setShopType(values.get("shopType"));
		
		//validate the bean
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ShopInformationIF>> violations = validator.validate(info);
		
		if(violations.isEmpty()){
		
			AutoBeanCodex.encode(autoBean).getPayload();
		
		}else{
			
			handleErrors();
			
		}
		
	}

	private void handleErrors() {
		
	}

}
