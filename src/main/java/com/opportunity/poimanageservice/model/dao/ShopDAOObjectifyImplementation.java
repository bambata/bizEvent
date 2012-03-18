package com.opportunity.poimanageservice.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.opportunity.poimanageservice.location.GeoBoxUtils;
import com.opportunity.poimanageservice.model.ModelConstants;
import com.opportunity.poimanageservice.model.Shop;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.ZoneHub;
import com.opportunity.poimanageservice.model.dao.spi.ShopDAO;
import com.opportunity.poimanageservice.model.dto.ShopInformation;

public class ShopDAOObjectifyImplementation extends DAOT implements ShopDAO {

	static {

		ObjectifyService.register(Shop.class);
		ObjectifyService.register(ShopDetails.class);
		ObjectifyService.register(ZoneHub.class);

	}

	private ShopInformation shopInformation;

	private ZoneHub hubInTheGeoBox;

	private Shop shop;

	private ShopDetails shopDetails;

	@Override
	public List<ZoneHub> retrieveHubsInLargeGeoBox(String location) {

		String[] coordinates = location.split(",");

		String geoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1);

		return ofy().query(ZoneHub.class)
				.filter("geoBoxLargeResolution", geoBox).list();

	}

	@Override
	public Map<Long, ShopDetails> retrieveListOfShopDetails(List<Long> keys) {

		Objectify objectify = ObjectifyService.beginTransaction();

		try {

			Map<Long, Shop> parents = objectify.get(Shop.class, keys);
			List<Key<ShopDetails>> listOfKeys = new ArrayList<Key<ShopDetails>>();
			for (Long key : parents.keySet()) {
				Shop shop = parents.get(key);
				listOfKeys.add(shop.getShopDetailsId());
			}

			Map<Key<ShopDetails>, ShopDetails> result = objectify.get(
					ShopDetails.class, listOfKeys);

			Map<Long, ShopDetails> toReturn = new HashMap<Long, ShopDetails>();

			for (Map.Entry<Key<ShopDetails>, ShopDetails> entry : result
					.entrySet()) {

				toReturn.put(entry.getKey().getId(), entry.getValue());

			}

			objectify.getTxn().commit();

			return toReturn;

		} finally {
			if (objectify.getTxn().isActive())
				objectify.getTxn().rollback();
		}

	}

	@Override
	public ShopDetails retrieveShopDetails(Long parentKey) {

		Objectify objectify = ObjectifyService.beginTransaction();

		try {

			Shop shop = objectify.get(Shop.class, parentKey);

			ShopDetails toReturn = objectify.get(shop.getShopDetailsId());

			objectify.getTxn().commit();

			return toReturn;

		} finally {
			if (objectify.getTxn().isActive())
				objectify.getTxn().rollback();
		}
	}

	
	@Override
	public List<Key<?>> persistShop(ShopInformation shopInfo) {

		List<Key<?>> toReturn = new ArrayList<Key<?>>();

		this.shopInformation = shopInfo;

		String[] coordinates = shopInformation.getGeoLocation().split(",");

		String smallGeoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.SMALL_GEO_BOX_RESOLUTION, 1);

		String bigGeoBox = GeoBoxUtils.computeGeoBox(coordinates[0],
				coordinates[1], ModelConstants.LARGE_GEO_BOX_RESOLUTION, 1);
		
		// set up the hub and persist the hub
		Objectify objectify = ObjectifyService.begin();
		hubInTheGeoBox = objectify.query(ZoneHub.class)
				.filter("geoBoxSmallResolution", smallGeoBox).get();

		// set up the shop
		shop = new Shop();

		shop.setLocation(shopInformation.getGeoLocation());

		shop.setName(shopInformation.getShopName());

		shop.setShopType(shopInformation.getShopType());

		shop.setGeoBoxLargeResolution(bigGeoBox);

		shop.setGeoBoxSmallResolution(smallGeoBox);

		// set up the shop details
		shopDetails = new ShopDetails(shopInformation);

		if (hubInTheGeoBox != null) {
			shop.setXMPPTopicName(hubInTheGeoBox.getXMPPTopicName());
		} else {
			hubInTheGeoBox = new ZoneHub();
			hubInTheGeoBox.setXMPPTopicName("Node_"
					+ Long.toString(new Date().getTime()));
			hubInTheGeoBox.setGeoBoxSmallResolution(smallGeoBox);
			hubInTheGeoBox.setGeoBoxLargeResolution(bigGeoBox);
			objectify.put(hubInTheGeoBox);
		}

		// Transaction to persist shop and shopDetails
		repeatInTransaction(new Transactable() {

			@Override
			public void run(DAOT daot) {

				Key<ZoneHub> keyZoneHub = new Key<ZoneHub>(ZoneHub.class,
						hubInTheGeoBox.getId());

				shop.setHub(keyZoneHub);

				Key<Shop> keyShop = ofy().put(shop);

				shopDetails.setShop(keyShop);

				ofy().put(shopDetails);

				ofy().getTxn().commit();

			}

		});
		
		Key<Shop> keyShop = new Key<Shop>(Shop.class, shop.getId());
		toReturn.add(new Key<ZoneHub>(ZoneHub.class, hubInTheGeoBox.getId()));
		toReturn.add(keyShop);
		toReturn.add(new Key<ShopDetails>(keyShop, ShopDetails.class, shopDetails
				.getId()));

		return toReturn;

	}
}
