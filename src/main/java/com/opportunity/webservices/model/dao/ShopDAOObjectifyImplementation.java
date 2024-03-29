package com.opportunity.webservices.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.opportunity.webservices.location.GeoBoxUtils;
import com.opportunity.webservices.model.ModelConstants;
import com.opportunity.webservices.model.Shop;
import com.opportunity.webservices.model.ShopDetails;
import com.opportunity.webservices.model.ZoneHub;
import com.opportunity.webservices.model.dao.DAOT;
import com.opportunity.webservices.model.dao.spi.ShopDAO;
import com.opportunity.webservices.model.dto.ShopInformation;

import static com.opportunity.webservices.model.dao.DAOT.*;

public class ShopDAOObjectifyImplementation extends DAOBase implements ShopDAO {

	static {

		ObjectifyService.register(Shop.class);
		ObjectifyService.register(ShopDetails.class);
		ObjectifyService.register(ZoneHub.class);

	}

	private ShopInformation shopInformation;

	private ZoneHub hubInTheGeoBox;

	private Shop shop;

	private ShopDetails shopDetails;

	private Long parentKey;

	@Override
	public List<ZoneHub> retrieveHubsInGeoBox(String geoBox) {

		return ofy().query(ZoneHub.class)
				.filter("geoBoxLargeResolution", geoBox).list();

	}

	@Override
	public Map<Long, ShopDetails> retrieveListOfShopDetails(List<Long> keys) {
		
		Map<Long, ShopDetails> toReturn = new HashMap<Long, ShopDetails>();
		
		for(Long key : keys){
			
			ShopDetails shopDetails = retrieveShopDetails(key);
			if(shopDetails != null)
				toReturn.put(key, shopDetails);
			
		}
		
		return toReturn;

	}

	@Override
	public ShopDetails retrieveShopDetails(Long parentKeyParam) {

		parentKey = parentKeyParam;

		DAOT.repeatInTransaction(new Transactable() {

			@Override
			public void run(DAOT daot) {

				try {
				
					shop = daot.ofy().get(Shop.class, parentKey);

					shopDetails = daot.ofy().get(shop.getShopDetailsId());
				
				} catch (NotFoundException e) {
					
					shopDetails = null;
					
				}

			}

		});

		return shopDetails;
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
		hubInTheGeoBox = ofy().query(ZoneHub.class)
				.filter("geoBoxSmallResolution", smallGeoBox).get();
		
		if (hubInTheGeoBox != null) {
			shop.setXMPPTopicName(hubInTheGeoBox.getXMPPTopicName());
		} else {
			hubInTheGeoBox = new ZoneHub();
			hubInTheGeoBox.setXMPPTopicName("Node_"
					+ Long.toString(new Date().getTime()));
			hubInTheGeoBox.setGeoBoxSmallResolution(smallGeoBox);
			hubInTheGeoBox.setGeoBoxLargeResolution(bigGeoBox);
			ofy().put(hubInTheGeoBox);
		}
		
		// set up the shop
		shop = new Shop();

		shop.setLocation(shopInformation.getGeoLocation());

		shop.setName(shopInformation.getShopName());

		shop.setShopType(shopInformation.getShopType());

		shop.setGeoBoxLargeResolution(bigGeoBox);

		shop.setGeoBoxSmallResolution(smallGeoBox);

		// set up the shop details
		shopDetails = new ShopDetails(shopInformation);

		

		// Transaction to persist shop and shopDetails
		repeatInTransaction(new Transactable() {

			@Override
			public void run(DAOT daot) {

				Key<ZoneHub> keyZoneHub = new Key<ZoneHub>(ZoneHub.class,
						hubInTheGeoBox.getId());

				shop.setHub(keyZoneHub);

				Key<Shop> keyShop = daot.ofy().put(shop);

				shopDetails.setShop(keyShop);

				daot.ofy().put(shopDetails);

			}

		});

		Key<Shop> keyShop = new Key<Shop>(Shop.class, shop.getId());
		toReturn.add(new Key<ZoneHub>(ZoneHub.class, hubInTheGeoBox.getId()));
		toReturn.add(keyShop);
		toReturn.add(new Key<ShopDetails>(keyShop, ShopDetails.class,
				shopDetails.getId()));

		return toReturn;

	}
}
