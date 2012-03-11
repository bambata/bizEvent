package com.opportunity.poimanageservice.model;

import java.util.Date;

import javax.persistence.Id;

import com.google.appengine.api.datastore.Email;

public class User {
	
	@Id
	private Long id;
	
	private Email email;
	
	private Date dateOfSubscription;
	
}
