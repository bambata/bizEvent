package com.opportunity.poimanageservice.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.opportunity.poimanageservice.location.GeoBoxUtils;
import com.opportunity.poimanageservice.model.ModelConstants;
import com.opportunity.poimanageservice.model.Shop;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.dao.spi.ShopDAO;
import com.opportunity.poimanageservice.model.dto.ShopInformation;

public class ShopDAOObjectifyImplementation implements ShopDAO {

	static {

		ObjectifyService.register(Shop.class);
		ObjectifyService.register(ShopDetails.class);

	}

	@Override
	public List<Shop> retrieveListOfMasterShopAtLargeScale(String location) {

		String[] coordinates = location.split(",");

		String geoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1);

		Objectify objectify = ObjectifyService.begin();

		return objectify.query(Shop.class).filter("geoBoxMasterShop", true)
				.filter("geoBoxLargeResolution", geoBox).list();

	}

	@Override
	public Map<Key<ShopDetails>, ShopDetails> retrieveListOfShopDetails(Map<Long, Long> keys) {

		Objectify objectify = ObjectifyService.begin();

		List<Key<ShopDetails>> computedKeys = new ArrayList<Key<ShopDetails>>();

		for (Map.Entry<Long, Long> key : keys.entrySet()) {

			computedKeys.add(new Key<ShopDetails>(new Key<Shop>(Shop.class, key
					.getKey()), ShopDetails.class, key.getValue()));
			
		}

		return objectify.get(computedKeys);

	}

	@Override
	public ShopDetails retrieveShopDetails(Long parentKey, Long childKey) {

		Objectify objectify = ObjectifyService.begin();

		return objectify.get(new Key<ShopDetails>(new Key<Shop>(Shop.class, parentKey), ShopDetails.class, childKey));

	}

	@Override
	public void persistShop(ShopInformation shopInformation) {

		Shop shop = new Shop();

		String[] coordinates = shopInformation.getGeoLocation().split(",");

		String smallGeoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.SMALL_GEO_BOX_RESOLUTION, 1);
		
		String bigGeoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1);
		
		shop.setLocation(shopInformation.getGeoLocation());
		
		shop.setName(shopInformation.getShopName());
		
		shop.setShopType(shopInformation.getShopType());
		
		shop.setGeoBoxLargeResolution(bigGeoBox);
		
		shop.setGeoBoxSmallResolution(smallGeoBox);
		
		ShopDetails shopDetails = new ShopDetails(shopInformation);
		
		Objectify objectify = ObjectifyService.beginTransaction();

		try {
			Shop shopInTheGeoBox = objectify.query(Shop.class).filter("geoBoxSmallResolution", smallGeoBox).get();
			
			if(shopInTheGeoBox != null){
				shop.setXMPPTopicName(shopInTheGeoBox.getXMPPTopicName());
			}else{
				shop.setXMPPTopicName("Node_" + Long.toString(new Date().getTime()));
			}
			
			objectify.put(shop);
			
			objectify.put(shopDetails);
			
			objectify.getTxn().commit();
			
		} finally {
			if (objectify.getTxn().isActive())
				objectify.getTxn().rollback();
		}

	}
}
