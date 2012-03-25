package com.opportunity.mainsite.shared;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;

public class TestShopInformation {

	@Test
	public void testJSONSerialization() {

		/*ApplicationAutoBeanFactory autoBeanFactory = AutoBeanFactorySource
				.create(ApplicationAutoBeanFactory.class);

		AutoBean<ShopInformationIF> autoBean = autoBeanFactory
				.create(ShopInformationIF.class);

		ShopInformationIF shopInfo = autoBean.as();

		shopInfo.setCity("Nice");
		shopInfo.setCountry("France");
		shopInfo.setDescription("Boulangerie du coin");
		shopInfo.setGeoLocation("123,456");
		shopInfo.setNumber(5);
		shopInfo.setZipCode(55456);
		shopInfo.setSubType("Subtype");
		shopInfo.setShopName("la mie caline");

		String jsonString = AutoBeanCodex.encode(autoBean).getPayload();
		assertNotNull(jsonString);

		Pattern pattern = Pattern.compile("(\"(\\w)\")");
		Matcher matcher = pattern.matcher(jsonString);

		Map<String, String> jsonMap = new HashMap<String, String>();
		while (matcher.find()) {
			
			jsonMap.put(matcher.group(1), matcher.group(2));
		
		}
		
		assertTrue(!jsonMap.isEmpty());*/
		

	}

}
