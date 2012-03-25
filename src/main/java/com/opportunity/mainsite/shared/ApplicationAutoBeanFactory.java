package com.opportunity.mainsite.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface ApplicationAutoBeanFactory extends AutoBeanFactory {
	
	AutoBean<ShopInformationIF> shopInformation();
	  
}
