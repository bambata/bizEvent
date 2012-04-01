package com.opportunity.webservices.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.opportunity.webservices.model.ShopDetails;


public class ShopInformation {

	private String shopName;

	private int zipCode;

	private String country;

	private String street;

	private int number;

	private String geoLocation;

	private String shopType;

	private String subType;

	private String description;

	public ShopInformation(){

	}

	public ShopInformation(ShopDetails shopDetails){

	  shopName = shopDetails.getShopName();
	  zipCode = shopDetails.getZipCode();
	  street = shopDetails.getStreet();
	  number = shopDetails.getNumber();
	  shopType = shopDetails.getShopType();
	  subType = shopDetails.getSubType();
	  description = shopDetails.getDescription();
	  country = shopDetails.getCountry();
	  geoLocation = shopDetails.getGeoLocation();
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

}
