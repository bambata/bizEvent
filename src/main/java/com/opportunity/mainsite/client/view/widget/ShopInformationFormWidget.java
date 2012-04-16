package com.opportunity.mainsite.client.view.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.swfupload.client.SWFUpload;
import org.swfupload.client.SWFUpload.WindowMode;
import org.swfupload.client.UploadBuilder;
import org.swfupload.client.SWFUpload.ButtonAction;
import org.swfupload.client.event.UploadProgressHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.opportunity.mainsite.shared.ShopInformation;
import com.opportunity.mainsite.shared.Error;

public class ShopInformationFormWidget extends Composite implements
		ShopInformationForm {

	@UiTemplate("ShopInformationFormWidget.ui.xml")
	interface MyUIBinder extends
			UiBinder<DockLayoutPanel, ShopInformationFormWidget> {
	};

	private static MyUIBinder uiBinder = GWT.create(MyUIBinder.class);

	private Map<String, Widget> propertyPathFieldMapping;

	Set<ConstraintViolation<ShopInformation>> violations;

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
	Button startUploadButton;

	@UiField
	TextBox email;
	
	@UiField
	TextBox test;

	ShopInformationFormObserver presenter;

	Error error;

	MapWidget map;

	SWFUpload swfUpload;

	StringBuilder locationForGoogleMaps = new StringBuilder();
	
	@UiFactory
	public MapWidget makeMapWidget() {

		LatLng center = LatLng.newInstance(43.683515, 7.265396);
		MapOptions opts = MapOptions.newInstance();
		opts.setZoom(12);
		opts.setCenter(center);
		opts.setMapTypeId(MapTypeId.ROADMAP);

		return new MapWidget(opts);
	}

	private ShopInformationFormState widgetState = ShopInformationFormState.standard;

	public ShopInformationFormObserver getPresenter() {
		return presenter;
	}

	public void setSuccessState() {
		this.widgetState = ShopInformationFormState.success;
	}

	@Override
	public void setPresenter(ShopInformationFormObserver presenter) {
		this.presenter = presenter;
	}

	public ShopInformationFormWidget() {

		initWidget(uiBinder.createAndBindUi(this));

	}

	@Override
	public Widget toWidget() {
		return this;
	}

	public Set<ConstraintViolation<ShopInformation>> getViolations() {
		return violations;
	}

	@Override
	public void updateMap(LatLng geolocation) {
		map.setCenter(geolocation);
	}

	@Override
	public void setViolations(
			Set<ConstraintViolation<ShopInformation>> violations) {
		this.violations = violations;
	}

	@Override
	public void setWidgetState(ShopInformationFormState state) {
		this.widgetState = state;
	}

	@Override
	public void setWidgetState(ShopInformationFormState state, Error error) {
		this.widgetState = state;
		this.error = error;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		UploadBuilder builder = new UploadBuilder();
		builder.setFileTypes("*.png;*.jpg;*.jpeg;*.gif");
		builder.setFileTypesDescription("Images");

		// Configure the button to display
		builder.setButtonPlaceholderID("upload");
		// builder.setButtonWidth(startUploadButton.getOffsetWidth());
		// builder.setButtonHeight(startUploadButton.getOffsetHeight());

		builder.setButtonWidth(106);
		builder.setButtonHeight(24);
		builder.setWindowMode(WindowMode.TRANSPARENT);

		builder.setButtonTextLeftPadding(7);
		builder.setButtonTextTopPadding(4);
		builder.setSwfUrl(GWT.getModuleName() + "/swfupload.swf");
		builder.setButtonAction(ButtonAction.SELECT_FILES);
		
		builder.setUploadProgressHandler(new UploadProgressHandler(){

			@Override
			public void onUploadProgress(UploadProgressEvent e) {
				e.getFile().getName();
			}
			
		});
		
		swfUpload = builder.build();
		
		
		// Initialize the mapping between property path and field
		// propertyPathFieldMapping.put("/shopName", shopName);
		// propertyPathFieldMapping.put("/country", country);
		// propertyPathFieldMapping.put("/description", description);
		// propertyPathFieldMapping.put("/street", street);
		// propertyPathFieldMapping.put("/number", number);
		// propertyPathFieldMapping.put("/zip", zip);
		// propertyPathFieldMapping.put("/city", city);
		// propertyPathFieldMapping.put("/state", state);
		// propertyPathFieldMapping.put("/email", email);
		
		street.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				handleNumberChange(event);
				
			}
		});
		
	}

	// Handlers
	@UiHandler("submitButton")
	void handleClick(ClickEvent evt) {

		Map<String, String> info = new HashMap<String, String>();
		info.put("shopName", shopName.getValue());
		info.put("shopType", shopType.getValue(shopType.getSelectedIndex()));
		info.put("street", street.getValue());
		info.put("description", description.getValue());

		presenter.onSubmitButtonClick(info);
	}

	@UiHandler("startUploadButton")
	void handleAddFilesClick(ClickEvent evt) {
		swfUpload.startUpload();
	}

	@UiHandler("clear")
	public void clear(ClickEvent e) {
		this.city.setText("");
		this.street.setText("");
		this.zip.setText("");
		this.description.setText("");
		this.number.setText("");
		this.shopName.setText("");
		this.email.setText("");
	}
	
	@UiHandler("test")
	public void handleTest(ChangeEvent e){
		Window.alert("Yo");
	}

	@UiHandler("country")
	public void handleCountryChange(ChangeEvent e) {
		locationForGoogleMaps.append(country.getValue(country
				.getSelectedIndex()) + " ");
		updateMapCenter(locationForGoogleMaps.toString());
	}
	
	@UiHandler("city")
	public void handleCityChange(ChangeEvent e) {
		locationForGoogleMaps.append(city.getValue() + " ");
		updateMapCenter(locationForGoogleMaps.toString());
	}

	@UiHandler("zip")
	public void handleZipChange(ChangeEvent e) {
		locationForGoogleMaps.append(zip.getValue() + " ");
		updateMapCenter(locationForGoogleMaps.toString());
	}

	/*@UiHandler("street")
	public void handleStreetChange(ChangeEvent e) {
		locationForGoogleMaps.append(street.getValue() + " ");
		updateMapCenter(locationForGoogleMaps.toString());
	}*/

	@UiHandler("number")
	public void handleNumberChange(ChangeEvent e) {
		locationForGoogleMaps.append(number.getValue() + " ");
		updateMapCenter(locationForGoogleMaps.toString());
	}

	private void updateMapCenter(String choosedCountry) {
		
		presenter.getAdresseLocation(locationForGoogleMaps.toString());
		
	}

}
