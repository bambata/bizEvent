package com.opportunity.poimanageservice.model.dao.spi;

import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.opportunity.poimanageservice.model.Shop;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.ZoneHub;
import com.opportunity.poimanageservice.model.dto.ShopInformation;

public interface ShopDAO {

	ShopDetails retrieveShopDetails(Long shopKey);
	
	List<Key<?>> persistShop(ShopInformation shopInformation);
	
	Map<Long, ShopDetails> retrieveListOfShopDetails(List<Long> keys);

	List<ZoneHub> retrieveHubsInGeoBox(String location);
	
}
