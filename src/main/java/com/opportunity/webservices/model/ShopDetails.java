package com.opportunity.webservices.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;
import com.opportunity.webservices.model.dto.ShopInformation;

@Unindexed
public class ShopDetails implements Serializable {

	private static final long serialVersionUID = -4375383331673481111L;

	@Id
	private Long Id;
	
	@Parent Key<Shop> shop;

	private String shopName;

	private int zipCode;

	private String country;

	private String street;

	private int number;

	private String geoLocation;

	private String shopType;

	private String subType;

	private String description;
	
	private Date registrationDate;
	
	public ShopDetails(){
		
	}
	
	public ShopDetails(ShopInformation shopInformation){
		
		shopName = shopInformation.getShopName();
		
		zipCode = shopInformation.getZipCode();
		
		street = shopInformation.getStreet();
		
		number = shopInformation.getNumber();
		
		geoLocation = shopInformation.getGeoLocation();
		
		shopType = shopInformation.getShopType();
		
		subType = shopInformation.getSubType();
		
		description = shopInformation.getDescription();
		
		country = shopInformation.getCountry();
		
		registrationDate = new Date();
		
	}
	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Key<Shop> getShop() {
		return shop;
	}

	public void setShop(Key<Shop> shop) {
		this.shop = shop;
	}
	
}
