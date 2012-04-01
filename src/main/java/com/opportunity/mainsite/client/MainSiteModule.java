package com.opportunity.mainsite.client;

import javax.validation.Validation;
import javax.validation.Validator;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;
import com.google.inject.Singleton;

public class MainSiteModule extends AbstractGinModule {

	@Override
	protected void configure() {
		
		bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
		
		bind(Validator.class).toProvider(ValidatorProvider.class).in(Singleton.class);
		
		bind(Scheduler.class).toProvider(SchedulerProvider.class);
		
	}
	
	static class ValidatorProvider implements Provider<Validator> {

		@Override
		public Validator get() {
			
			return Validation.buildDefaultValidatorFactory().getValidator();
		}
		
	}
	
	static class SchedulerProvider implements Provider<Scheduler> {

		@Override
		public Scheduler get() {
			
			return Scheduler.get();
		}
		
	}
	
}
