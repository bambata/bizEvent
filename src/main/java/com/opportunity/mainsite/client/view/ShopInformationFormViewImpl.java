package com.opportunity.mainsite.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;



public class ShopInformationFormViewImpl extends Composite implements ShopInformationFormView {
	
	@UiTemplate("ShopInformationView.ui.xml")
	interface MyUIBinder extends UiBinder<HTMLPanel, ShopInformationFormView> {};
	private static MyUIBinder uiBinder = GWT.create(MyUIBinder.class);
	
	@UiField
	TextBox shopName;

	@UiField
	ListBox shopType;
	
	ShopInformationFormObserver presenter;
	
	public ShopInformationFormObserver getPresenter() {
		return presenter;
	}
	
	@Override
	public void setPresenter(ShopInformationFormObserver presenter) {
		this.presenter = presenter;
	}

	public ShopInformationFormViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget toWidget(){
		return this.toWidget();
	}
	
	
	
}
