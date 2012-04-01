package com.opportunity.mainsite.shared;

public class Error {

  int errorCode;

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public void setGenericMessage(String genericMessage) {
    this.genericMessage = genericMessage;
  }

  String genericMessage;

  public String getGenericMessage() {
    return null;
  }

  public int getErrorCode() {
    return 0;
  }

}
