package com.opportunity.poimanageservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.opportunity.poimanageservice.model.dto.ShopInformation;

@Controller
@RequestMapping("/shopManagementService")
public final class ShopManagementService {

	@RequestMapping(value = "/shop/{shopId}.*", method = RequestMethod.GET)
	public void getInformationOfShop(@PathVariable String shopId) {
		
		
	}
	
	@RequestMapping(value = "/shoplist/{shopIdList}.*", method = RequestMethod.GET)
	public void getInformationOfShopList(@PathVariable String listOfShopId) {
		
		
		
	}
	
	@RequestMapping(value="/hubs.*", method = RequestMethod.GET)
	public void getHubList(@RequestParam String location){
		
	}
	
	@RequestMapping(value="/shop/create.*", method = RequestMethod.POST)
	public void persistNewShop(@RequestBody ShopInformation shopInformation){
		
		
		
	}
	
	@RequestMapping(value="/shop/remove/{shopId}", method = RequestMethod.POST)
	public void removeShop(@PathVariable String shopId){
		
	}
	
}
