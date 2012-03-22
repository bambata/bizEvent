package com.opportunity.webservices.model.dao.spi;

import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.opportunity.webservices.model.Shop;
import com.opportunity.webservices.model.ShopDetails;
import com.opportunity.webservices.model.ZoneHub;
import com.opportunity.webservices.model.dto.ShopInformation;

public interface ShopDAO {

	ShopDetails retrieveShopDetails(Long shopKey);
	
	List<Key<?>> persistShop(ShopInformation shopInformation);
	
	Map<Long, ShopDetails> retrieveListOfShopDetails(List<Long> keys);

	List<ZoneHub> retrieveHubsInGeoBox(String location);
	
}
