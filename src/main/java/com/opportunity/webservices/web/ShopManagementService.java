package com.opportunity.webservices.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
import com.opportunity.webservices.location.GeoBoxUtils;
import com.opportunity.webservices.model.ModelConstants;
import com.opportunity.webservices.model.ShopDetails;
import com.opportunity.webservices.model.ZoneHub;
import com.opportunity.webservices.model.dao.ShopDAOObjectifyImplementation;
import com.opportunity.webservices.model.dao.spi.ShopDAO;
import com.opportunity.webservices.model.dto.ShopInformation;
import com.opportunity.webservices.model.dto.ZoneHubInformation;
import com.opportunity.webservices.web.exceptions.ResourceDoesNotExistException;

@Controller
@RequestMapping("shopManagementService")
public final class ShopManagementService {

  @RequestMapping(value = "/shop/{shopId:\\d+}", method = RequestMethod.GET)
  public @ResponseBody
  ShopInformation getInformationOfShop(@PathVariable String shopId, ModelMap model)
      throws ResourceDoesNotExistException {

    String key = shopId;
    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
    ShopDetails shopDetails = (ShopDetails)syncCache.get(key);

    if (shopDetails == null) {
      ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
      shopDetails = shopDAO.retrieveShopDetails(new Long(shopId));
    }

    if (shopDetails == null)
      throw new ResourceDoesNotExistException("Shop with id " + shopId + " does not exist in datastore.");

    syncCache.put(key, shopDetails);

    return new ShopInformation(shopDetails);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/shop/list/{listOfShopId:\\d+(?:,\\d*)*}", method = RequestMethod.GET)
  public @ResponseBody
  Map<Long, ShopInformation> getInformationOfShopList(@PathVariable String listOfShopId)
      throws ResourceDoesNotExistException {

    String[] shopIds = listOfShopId.split(",");

    if (shopIds.length > 5)
      throw new ResourceDoesNotExistException("Too many shops in list, application does not serve this resource");

    List<Long> keys = new ArrayList<Long>();

    Arrays.sort(shopIds);
    StringBuilder cacheKeyConstruct = new StringBuilder();
    String preventShopId = "";

    for (String shopId : shopIds) {
      if (!shopIds.equals(preventShopId)) {
        cacheKeyConstruct.append(shopIds + ",");
        keys.add(new Long(shopId));
      }

      preventShopId = shopId;
    }

    String cacheKey = cacheKeyConstruct.toString();

    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

    Map<Long, ShopInformation> shopDetails = (Map<Long, ShopInformation>)syncCache.get(cacheKey);

    if (shopDetails == null) {
      ShopDAO shopDAO = new ShopDAOObjectifyImplementation();
      Map<Long, ShopDetails> result = shopDAO.retrieveListOfShopDetails(keys);

      shopDetails = new HashMap<Long, ShopInformation>();

      for (Long key : result.keySet()) {
        shopDetails.put(key, new ShopInformation(result.get(key)));
      }

      if (shopDetails.isEmpty())
        throw new ResourceDoesNotExistException("None of the shop exists, " + listOfShopId);

      syncCache.put(cacheKey, shopDetails);
    }

    return shopDetails;

  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value = "/hubs/{location}", method = RequestMethod.GET)
  public List<ZoneHubInformation> getHubList(@RequestParam String location) {

    String[] coordinates = location.split(",");

    String geoBox = GeoBoxUtils.computeGeoBox(coordinates[0], coordinates[1], ModelConstants.LARGE_GEO_BOX_RESOLUTION,
        1);

    ShopDAO shopDAO = new ShopDAOObjectifyImplementation();

    MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

    List<ZoneHub> zoneHubs = (List<ZoneHub>)syncCache.get(geoBox);

    if (zoneHubs == null) {
      zoneHubs = shopDAO.retrieveHubsInGeoBox(geoBox);

      if (!zoneHubs.isEmpty())
        syncCache.put(geoBox, zoneHubs);
    }

    List<ZoneHubInformation> toReturn = new ArrayList<ZoneHubInformation>();

    for (ZoneHub hub : zoneHubs) {

      toReturn.add(new ZoneHubInformation(hub));

    }

    return toReturn;

  }

  @RequestMapping(value = "/shop/create", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public void persistNewShop(@RequestBody @Valid ShopInformation shopInformation, BindingResult shopInfoBindingResult) {

    ShopDAO shopDAO = new ShopDAOObjectifyImplementation();

    shopDAO.persistShop(shopInformation);
  }

  @RequestMapping(value = "/shop/remove/{shopId}", method = RequestMethod.POST)
  public void removeShop(@PathVariable String shopId) {

  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ResponseBody
  public String handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {

    BindingResult validationResult = error.getBindingResult();

    return "Bad request: " + error.getMessage();

  }

  @ExceptionHandler(ResourceDoesNotExistException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ResponseBody
  public com.opportunity.webservices.model.dto.Error handleResourceDoesNotExistException(
      ResourceDoesNotExistException error) {

    return new com.opportunity.webservices.model.dto.Error("Not found: " + error.getMessage(), "Not found: " +
        error.getMessage(), 404);

  }

}
