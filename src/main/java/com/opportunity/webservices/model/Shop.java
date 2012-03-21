package com.opportunity.webservices.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
public class Shop {
    
	@Id
    private Long id;
	
	private String Name;
	
	@Indexed
    private String geoBoxLargeResolution;
    
	@Indexed
    private String geoBoxSmallResolution;
	
	@Indexed
	private String shopType;
    
	private String location;
    
    private Key<ShopDetails> shopDetailsId;
    
    private Key<ZoneHub> hub;
    
    private String XMPPTopicName;
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getGeoBoxLargeResolution() {
		return geoBoxLargeResolution;
	}

	public void setGeoBoxLargeResolution(String geoBoxLargeResolution) {
		this.geoBoxLargeResolution = geoBoxLargeResolution;
	}

	public String getGeoBoxSmallResolution() {
		return geoBoxSmallResolution;
	}

	public void setGeoBoxSmallResolution(String geoBoxSmallResolution) {
		this.geoBoxSmallResolution = geoBoxSmallResolution;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Key<ShopDetails> getShopDetailsId() {
		return shopDetailsId;
	}

	public void setShopDetailsId(Key<ShopDetails> shopDetailsId) {
		this.shopDetailsId = shopDetailsId;
	}

	public String getXMPPTopicName() {
		return XMPPTopicName;
	}

	public void setXMPPTopicName(String xMPPTopicName) {
		XMPPTopicName = xMPPTopicName;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public void setHub(Key<ZoneHub> hub) {
		this.hub = hub;
	}

	public Key<ZoneHub> getHub() {
		return hub;
	}
    
}
