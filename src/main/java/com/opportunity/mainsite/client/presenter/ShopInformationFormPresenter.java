package com.opportunity.mainsite.client.presenter;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.opportunity.mainsite.client.AppController;
import com.opportunity.mainsite.client.view.widget.ShopInformationForm;
import com.opportunity.mainsite.client.view.widget.ShopInformationFormWidget;
import com.opportunity.mainsite.shared.ErrorIF;
import com.opportunity.mainsite.shared.ShopInformationIF;

public class ShopInformationFormPresenter implements ShopInformationForm.ShopInformationFormObserver, Presenter {

  private static final String SHOP_MANAGEMENT_SHOP_PERSISTER = GWT.getModuleBaseURL() +
      "shopManagementService/shop/create";

  private static final String FILE_SERVICE_CALLBACK = GWT.getModuleBaseURL() + "fileService/callbackUrl";

  private static Logger logger = Logger.getLogger(ShopInformationFormPresenter.class.getName());

  private ShopInformationForm view;

  private Scheduler scheduler = Scheduler.get();

  @Override
  public void go(HasWidgets container) {
    container.clear();
    view = new ShopInformationFormWidget();
    view.setPresenter(this);
    container.add(view.toWidget());
  }

  @Override
  public void onSubmitButtonClick(Map<String, String> values) {



  }

  @Override
  public void onAddedFile() {



  }

  private void submitForm(Map<String, String> values) {

    AutoBean<ShopInformationIF> autoBean = AppController.applicationAutoBeanFactory.shopInformation();

    // create the bean
    ShopInformationIF info = autoBean.as();
    info.setCountry(values.get("country"));
    info.setDescription(values.get("description"));
    info.setNumber(Integer.parseInt(values.get("number")));
    info.setStreet(values.get("street"));
    info.setShopName(values.get("shopName"));
    info.setShopType(values.get("shopType"));

    // validate the bean
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<ShopInformationIF>> violations = validator.validate(info);

    if (violations.isEmpty()) {

      String jsonPayload = AutoBeanCodex.encode(autoBean).getPayload();

      // call backend service
      RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, SHOP_MANAGEMENT_SHOP_PERSISTER);

      try {

        Request request = builder.sendRequest(jsonPayload, new RequestCallback() {

          @Override
          public void onError(Request request, Throwable exception) {
            logger.log(Level.WARNING, exception.getMessage());
          }

          @Override
          public void onResponseReceived(Request request, Response response) {

            switch (response.getStatusCode()) {

            // Request successfully processed
            case 200:
              view.setWidgetState(ShopInformationForm.ShopInformationFormState.success);
              break;

            // functional error
            case 500:
              AutoBean<ErrorIF> errorAutoBeanWrapper = AutoBeanCodex.decode(AppController.applicationAutoBeanFactory,
                  ErrorIF.class, response.getText());
              ErrorIF error = errorAutoBeanWrapper.as();
              view.setWidgetState(ShopInformationForm.ShopInformationFormState.error, error);
              break;

            // technical error
            default:
              break;
            }

            if (200 == response.getStatusCode()) {

            }
            else {
              response.getText();

            }
          }
        });
      }
      catch (RequestException e) {

      }

    }
    else {

      view.setViolations(violations);

    }

  }

}
