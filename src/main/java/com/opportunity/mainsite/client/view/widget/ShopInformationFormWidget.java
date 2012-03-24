package com.opportunity.mainsite.client.view.widget;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ShopInformationFormWidget extends Composite implements ShopInformationForm {
	
	@UiTemplate("ShopInformationFormWidget.ui.xml")
	interface MyUIBinder extends UiBinder<HTMLPanel, ShopInformationFormWidget> {};
	private static MyUIBinder uiBinder = GWT.create(MyUIBinder.class);
	
	@UiField
	TextBox shopName;

	@UiField
	ListBox shopType;
	
	@UiField
	ListBox country;
	
	@UiField
	TextArea description;
	
	@UiField
	TextBox street;
	
	@UiField
	TextBox number;
	
	@UiField
	Button submitButton;
	
	@UiField
	TextBox zip;
	
	@UiField
	TextBox city;
	
	@UiField
	TextBox state;
	
	ShopInformationFormObserver presenter;
	
	public ShopInformationFormObserver getPresenter() {
		return presenter;
	}
	
	@Override
	public void setPresenter(ShopInformationFormObserver presenter) {
		this.presenter = presenter;
	}

	public ShopInformationFormWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public Widget toWidget(){
		return this;
	}
	
	@UiHandler("submitButton")
	void handleClick(ClickEvent evt){
		
		Map<String, String> info = new HashMap<String, String>();
		info.put("shopName", shopName.getValue());
		info.put("shopType", shopType.getValue(shopType.getSelectedIndex()));
		info.put("street", street.getValue());
		info.put("description", description.getValue());
		
		presenter.onSubmitButtonClick(info);
	}
	
	@UiHandler("clear") 
    public void clear(ClickEvent e){
            this.city.setText("");
            this.state.setText("");
            this.street.setText("");
            this.zip.setText("");
            this.description.setText("");
            this.number.setText("");
            this.shopName.setText("");
    }
	
	
	
}
