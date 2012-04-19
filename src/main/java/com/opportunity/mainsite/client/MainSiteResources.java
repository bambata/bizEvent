package com.opportunity.mainsite.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.opportunity.mainsite.client.css.MainsiteCSSResource;
import com.opportunity.mainsite.client.css.SkinCSSResource;

public interface MainSiteResources extends ClientBundle {
	
	public static final MainSiteResources INSTANCE = GWT.create(MainSiteResources.class);
	
	@Source("css/skin.css")
	SkinCSSResource getSkinStyle();
	
	@Source("css/mainsite.css")
	MainsiteCSSResource getMainsiteStyle();
	
	@Source("constants")
	ApplicationConstants getApplicationConstants();
	
}
