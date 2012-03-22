package com.opportunity.webservices.model.dto;

public class Error {

  public Error(){

  }

  public Error(String message, String localizedMessage, int errorCode){
    this.message = message;
    this.localizedMessage = localizedMessage;
    this.errorCode = errorCode;
  }

  String message;

  String localizedMessage;

  int errorCode;

}
