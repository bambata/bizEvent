package com.opportunity.poimanageservice.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.opportunity.poimanageservice.model.ShopDetails;
import com.opportunity.poimanageservice.model.ZoneHub;
import com.opportunity.poimanageservice.model.dao.ShopDAOObjectifyImplementation;
import com.opportunity.poimanageservice.model.dao.spi.ShopDAO;
import com.opportunity.poimanageservice.model.dto.ShopInformation;
import com.opportunity.poimanageservice.model.dto.ZoneHubInformation;

@Controller
@RequestMapping("/shopManagementService")
public final class ShopManagementService {

	@RequestMapping(value = "/shop/{shopId:\\d*}", method = RequestMethod.GET)
	public @ResponseBody
	ShopInformation getInformationOfShop(@PathVariable String shopId,
			@PathVariable String shopDetailsId, ModelMap model) {

		String key = shopId;
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		ShopDetails shopDetails = (ShopDetails) syncCache.get(key);

		if (shopDetails == null) {
			ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
			shopDetails = shopDAO.retrieveShopDetails(new Long(shopId));
			syncCache.put(key, shopDetails);
		}

		return new ShopInformation(shopDetails);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/shop/{listOfShopId:\\d*}", method = RequestMethod.GET)
	public @ResponseBody
	Map<Long, ShopInformation> getInformationOfShopList(
			@PathVariable String listOfShopId) {

		String[] shopIds = listOfShopId.split(",");

		List<Long> keys = new ArrayList<Long>();

		Arrays.sort(shopIds);
		StringBuilder cacheKeyConstruct = new StringBuilder();
		for (String shopId : shopIds) {
			cacheKeyConstruct.append(shopIds + ",");
			keys.add(new Long(shopId));
		}

		String cacheKey = cacheKeyConstruct.toString();

		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

		Map<Long, ShopInformation> shopDetails = (Map<Long, ShopInformation>) syncCache
				.get(cacheKey);

		if (shopDetails == null) {
			ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
			Map<Long, ShopDetails> result = shopDAO
					.retrieveListOfShopDetails(keys);
			for (Long key : result.keySet()) {
				if (shopDetails != null)
					shopDetails.put(key, new ShopInformation(result.get(key)));
			}
			syncCache.put(cacheKey, shopDetails);
		}

		return shopDetails;

	}

	@RequestMapping(value = "/hubs/{location}", method = RequestMethod.GET)
	public List<ZoneHubInformation> getHubList(@RequestParam String location) {
		
		ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
		
		List<ZoneHub> zoneHubs = shopDAO.retrieveHubsInLargeGeoBox(location);
		
		List<ZoneHubInformation> toReturn = new ArrayList<ZoneHubInformation>();
		for(ZoneHub hub : zoneHubs){
			
			toReturn.add(new ZoneHubInformation(hub));
			
		}
		
		return toReturn;
		
	}

	@RequestMapping(value = "/shop/create", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public void persistNewShop(
			@RequestBody @Valid ShopInformation shopInformation, BindingResult shopInfoBindingResult) {
		
		ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
		
		shopDAO.persistShop(shopInformation);
	}

	@RequestMapping(value = "/shop/remove/{shopId}", method = RequestMethod.POST)
	public void removeShop(@PathVariable String shopId) {
		
	}
	

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleMethodArgumentNotValidException(
            MethodArgumentNotValidException error) {
        
    	BindingResult validationResult = error.getBindingResult();
    	List<ObjectError> errors = validationResult.getAllErrors();
    	
    	return "Bad request: " + error.getMessage();
    	
    }
	
}
