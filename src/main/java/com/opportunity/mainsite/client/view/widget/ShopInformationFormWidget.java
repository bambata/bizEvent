package com.opportunity.mainsite.client.view.widget;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.swfupload.client.SWFUpload;
import org.swfupload.client.SWFUpload.WindowMode;
import org.swfupload.client.UploadBuilder;
import org.swfupload.client.SWFUpload.ButtonAction;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.opportunity.mainsite.shared.ShopInformation;
import com.opportunity.mainsite.shared.Error;

public class ShopInformationFormWidget extends Composite implements
		ShopInformationForm {

	@UiTemplate("ShopInformationFormWidget.ui.xml")
	interface MyUIBinder extends UiBinder<HTMLPanel, ShopInformationFormWidget> {
	};

	private static MyUIBinder uiBinder = GWT.create(MyUIBinder.class);
	
	private Map<String, Widget> propertyPathFieldMapping;
	
	Set<ConstraintViolation<ShopInformation>> violations;
	
	@UiField
	InputField<TextBox> shopName;

	@UiField
	InputField<ListBox> shopType;

	@UiField
	InputField<ListBox> country;

	@UiField
	InputField<TextArea> description;

	@UiField
	InputField<TextBox> street;

	@UiField
	InputField<TextBox> number;

	@UiField
	Button submitButton;

	@UiField
	InputField<TextBox> zip;

	@UiField
	InputField<TextBox> city;

	@UiField
	InputField<TextBox> state;

	@UiField
	Button startUploadButton;

	@UiField
	InputField<TextBox> email;
	
	ShopInformationFormObserver presenter;

	Error error;

	MapWidget map;

	SWFUpload swfUpload;

	@UiFactory
	public MapWidget makeMapWidget() {
	
		LatLng center = LatLng.newInstance(49.496675, -102.65625);
		MapOptions opts = MapOptions.newInstance();
		opts.setZoom(4);
		opts.setCenter(center);
		opts.setMapTypeId(MapTypeId.HYBRID);

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

	@UiHandler("submitButton")
	void handleClick(ClickEvent evt) {

		Map<String, String> info = new HashMap<String, String>();
		info.put("shopName", shopName.getInput().getValue());
		info.put("shopType", shopType.getInput().getValue(shopType.getInput().getSelectedIndex()));
		info.put("street", street.getInput().getValue());
		info.put("description", description.getInput().getValue());

		presenter.onSubmitButtonClick(info);
	}

	@UiHandler("startUploadButton")
	void handleAddFilesClick(ClickEvent evt) {
		swfUpload.startUpload();
	}

	@UiHandler("clear")
	public void clear(ClickEvent e) {
		this.city.getInput().setText("");
		this.state.getInput().setText("");
		this.street.getInput().setText("");
		this.zip.getInput().setText("");
		this.description.getInput().setText("");
		this.number.getInput().setText("");
		this.shopName.getInput().setText("");
		this.email.getInput().setText("");
	}

	public Set<ConstraintViolation<ShopInformation>> getViolations() {
		return violations;
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
		builder.setButtonWidth(startUploadButton.getOffsetWidth());
		builder.setButtonHeight(startUploadButton.getOffsetHeight());
		builder.setWindowMode(WindowMode.TRANSPARENT);

		builder.setButtonTextLeftPadding(7);
		builder.setButtonTextTopPadding(4);
		builder.setSwfUrl(GWT.getModuleName() + "/swfupload.swf");
		builder.setButtonAction(ButtonAction.SELECT_FILES);

		swfUpload = builder.build();
		
		
		//Initialize the mapping between property path and field
//		propertyPathFieldMapping.put("/shopName", shopName);
//		propertyPathFieldMapping.put("/country", country);
//		propertyPathFieldMapping.put("/description", description);
//		propertyPathFieldMapping.put("/street", street);
//		propertyPathFieldMapping.put("/number", number);
//		propertyPathFieldMapping.put("/zip", zip);
//		propertyPathFieldMapping.put("/city", city);
//		propertyPathFieldMapping.put("/state", state);
//		propertyPathFieldMapping.put("/email", email);
		
	}

}
