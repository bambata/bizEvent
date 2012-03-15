package com.opportunity.poimanageservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.dao.ShopDAOObjectifyImplementation;
import com.opportunity.poimanageservice.model.dao.spi.ShopDAO;
import com.opportunity.poimanageservice.model.dto.ShopInformation;

@Controller
@RequestMapping("/shopManagementService")
public final class ShopManagementService {

	@RequestMapping(value = "/shop/{shopId:\\d*}/{shopDetailsId:\\d*}.*", method = RequestMethod.GET)
	public @ResponseBody ShopInformation getInformationOfShop(@PathVariable String shopId,
			@PathVariable String shopDetailsId, ModelMap model) {

		String key = shopId + "," + shopDetailsId;
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		ShopDetails shopDetails = (ShopDetails) syncCache.get(key);

		if (shopDetails == null) {
			ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
			shopDetails = shopDAO.retrieveShopDetails(new Long(shopId), new Long(shopDetailsId));
			syncCache.put(key, shopDetails);
		}

		return new ShopInformation(shopDetails);
	}

	@RequestMapping(value = "/shoplist/{shopIdList}.*", method = RequestMethod.GET)
	public void getInformationOfShopList(@PathVariable String listOfShopId) {

	}

	@RequestMapping(value = "/hubs.*", method = RequestMethod.GET)
	public void getHubList(@RequestParam String location) {

	}

	@RequestMapping(value = "/shop/create.*", method = RequestMethod.POST)
	public void persistNewShop(@RequestBody ShopInformation shopInformation) {

	}

	@RequestMapping(value = "/shop/remove/{shopId}", method = RequestMethod.POST)
	public void removeShop(@PathVariable String shopId) {

	}

}
