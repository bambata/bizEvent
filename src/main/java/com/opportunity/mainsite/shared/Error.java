package com.opportunity.mainsite.shared;

public class Error implements ErrorIF {

  int errorCode;

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public void setGenericMessage(String genericMessage) {
    this.genericMessage = genericMessage;
  }

  String genericMessage;

  @Override
  public String getGenericMessage() {
    return null;
  }

  @Override
  public int getErrorCode() {
    return 0;
  }

}
