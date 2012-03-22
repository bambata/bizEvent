package com.opportunity.webservices.web.exceptions;

import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class ResourceDoesNotExistException extends ServletException {
	
	public ResourceDoesNotExistException() {
		super();
	}
	
	public ResourceDoesNotExistException(String message){
		super(message);
	}
	
	
}
