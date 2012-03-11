package com.opportunity.poimanageservice.model.dao.spi;

import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.opportunity.poimanageservice.model.Shop;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.dto.ShopInformation;

public interface ShopDAO {

	public List<Shop> retrieveListOfMasterShopAtLargeScale(String location);
	
	public Map<Key<ShopDetails>, ShopDetails> retrieveListOfShopDetails(Map<Long, Long> keys);
	
	public ShopDetails retrieveShopDetails(Long shopKey, Long shopDetailsKey);
	
	public void persistShop(ShopInformation shopInformation);
	
}
