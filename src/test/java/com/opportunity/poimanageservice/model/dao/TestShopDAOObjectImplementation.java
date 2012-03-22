package com.opportunity.poimanageservice.model.dao;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.opportunity.webservices.location.GeoBoxUtils;
import com.opportunity.webservices.model.ModelConstants;
import com.opportunity.webservices.model.Shop;
import com.opportunity.webservices.model.ShopDetails;
import com.opportunity.webservices.model.ShopType;
import com.opportunity.webservices.model.ZoneHub;
import com.opportunity.webservices.model.dao.ShopDAOObjectifyImplementation;
import com.opportunity.webservices.model.dao.spi.ShopDAO;
import com.opportunity.webservices.model.dto.ShopInformation;

public class TestShopDAOObjectImplementation {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final ShopDAO shopDAO = new ShopDAOObjectifyImplementation();

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void retrieveHubSuccessfully() {

		// create and persist hub
		String lat = "43.7032033";
		String lon = "7.2634634";

		ZoneHub hub = new ZoneHub();
		hub.setGeoBoxLargeResolution(GeoBoxUtils.computeGeoBox(lat, lon,
				ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1));
		hub.setGeoBoxSmallResolution(GeoBoxUtils.computeGeoBox(lat, lon,
				ModelConstants.SMALL_GEO_BOX_RESOLUTION, 1));
		hub.setXMPPTopicName("bizEvent.com/Node_1234567890");
		hub.setZoneName("test");

		Objectify objectify = ObjectifyService.begin();
		objectify.put(hub);

		List<ZoneHub> result = shopDAO
				.retrieveHubsInGeoBox(GeoBoxUtils.computeGeoBox("43.70328553", "7.2634459", ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1));

		assertNotNull(result);
		assertTrue(result.size() == 1);
		assertEquals(result.get(0), hub);

	}

	@Test
	public void persistShopSuccessfully(){
		
		ShopInformation shopInformation = new ShopInformation();
		
		String lat = "47.218371";
		String lon = "-1.553621";
		
		String country = "FR";
		String description = "Boulangerie bas de gamme";
		String name = "La Mie Caline";
		String street = "Place du Général de Gaulle";
		int zipCode = 44000;
		
		shopInformation.setCountry(country);
		shopInformation.setDescription(description);
		shopInformation.setGeoLocation(lat + "," + lon);
		shopInformation.setNumber(5);
		shopInformation.setShopName(name);
		shopInformation.setShopType(ShopType.Food.toString());
		shopInformation.setStreet(street);
		shopInformation.setZipCode(zipCode);
		
		List<Key<?>> result = shopDAO.persistShop(shopInformation);

		Objectify objectify = ObjectifyService.begin();
		
		Shop retrievedShop = objectify.get((Key<Shop>)result.get(1));
		ShopDetails retrievedShopDetails = objectify.get((Key<ShopDetails>)result.get(2));
		ZoneHub zoneHub = objectify.get((Key<ZoneHub>)result.get(0));
		
		assertEquals(3, result.size());
		assertNotNull(retrievedShop);
		assertEquals(retrievedShop.getLocation(), lat + "," + lon);
		assertEquals(retrievedShop.getName(), name);
		assertEquals(retrievedShop.getShopType(), ShopType.Food.toString());
		assertEquals(retrievedShop.getLocation(), lat + "," + lon);
		
		assertEquals(retrievedShopDetails.getCountry(), "FR");
		assertEquals(retrievedShopDetails.getDescription(), description);
		assertEquals(retrievedShopDetails.getGeoLocation(), lat + "," + lon);
		assertEquals(retrievedShopDetails.getShopName(), name);
		assertEquals(retrievedShopDetails.getShopType(), ShopType.Food.toString());
		assertEquals(retrievedShopDetails.getShop(), result.get(1));
		assertEquals(retrievedShopDetails.getNumber(), 5);
		
		assertNotNull(zoneHub);
		assertEquals(GeoBoxUtils.computeGeoBox(lat, lon, ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1), zoneHub.getGeoBoxLargeResolution());
		assertEquals(GeoBoxUtils.computeGeoBox(lat, lon, ModelConstants.SMALL_GEO_BOX_RESOLUTION, 1), zoneHub.getGeoBoxSmallResolution());
	}

}
