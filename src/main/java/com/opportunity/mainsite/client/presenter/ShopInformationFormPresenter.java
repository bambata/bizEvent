package com.opportunity.mainsite.client.presenter;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.opportunity.mainsite.client.view.widget.ShopInformationForm;
import com.opportunity.mainsite.shared.ShopInformation;

@Singleton
public class ShopInformationFormPresenter implements
		ShopInformationForm.ShopInformationFormObserver, Presenter {

	private static final String SHOP_MANAGEMENT_SHOP_PERSISTER = GWT
			.getModuleBaseURL() + "shopManagementService/shop/create";

	private static final String FILE_SERVICE_CALLBACK = GWT.getModuleBaseURL()
			+ "fileService/callbackUrl";

	private static Logger logger = Logger
			.getLogger(ShopInformationFormPresenter.class.getName());

	private ShopInformationForm view;

	private Scheduler scheduler;

	private Validator validator;

	private EventBus applicationGlobalEventBus;

	public ShopInformationFormPresenter() {

	}

	@Inject
	public ShopInformationFormPresenter(Scheduler scheduler,
			Validator validator, ShopInformationForm view,
			EventBus applicationGlobalEventBus) {
		this.scheduler = scheduler;
		this.validator = validator;
		this.view = view;
		this.applicationGlobalEventBus = applicationGlobalEventBus;
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		view.setPresenter(this);
		container.add(view.toWidget());
	}

	@Override
	public void onSubmitButtonClick(Map<String, String> values) {

		submitForm(values);

	}

	@Override
	public void onAddedFile() {

	}

	private void submitForm(Map<String, String> values) {

		// create the bean
		ShopInformation info = new ShopInformation();
		info.setCountry(values.get("country"));
		info.setDescription(values.get("description"));
		info.setNumber(values.get("number"));
		info.setStreet(values.get("street"));
		info.setShopName(values.get("shopName"));
		info.setShopType(values.get("shopType"));

		// validate the bean
		Set<ConstraintViolation<ShopInformation>> violations = validator
				.validate(info);

		if (violations.isEmpty()) {

			// String jsonPayload = AutoBeanCodex.encode(autoBean).getPayload();

			// call backend service
			RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
					SHOP_MANAGEMENT_SHOP_PERSISTER);

			try {

				Request request = builder.sendRequest(info.toString(),
						new RequestCallback() {

							@Override
							public void onError(Request request,
									Throwable exception) {
								logger.log(Level.WARNING,
										exception.getMessage());
							}

							@Override
							public void onResponseReceived(Request request,
									Response response) {

								switch (response.getStatusCode()) {

								// Request successfully processed
								case 200:
									view.setWidgetState(ShopInformationForm.ShopInformationFormState.success);
									break;

								// functional error
								case 500:

									break;

								// technical error
								default:
									break;
								}

								if (200 == response.getStatusCode()) {

								} else {
									response.getText();

								}
							}
						});
			} catch (RequestException e) {

			}

		} else {

			view.setViolations(violations);

		}

	}

}
