package com.opportunity.mainsite.shared;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.google.gwt.core.client.JavaScriptObject;

public class ShopInformation {

	private String shopName;

	private String zipCode;

	private String country;

	private String street;

	private String number;

	private String city;

	private String geoLocation;

	private String shopType;

	private String subType;

	private String description;

	private String email;

	public ShopInformation() {

	}

	@NotNull
	@Pattern(regexp = ".{3,50}")
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@NotNull
	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotNull
	@Pattern(regexp = "\\d{1,10}")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return Country code ISO 3166-1 alpha-3
	 */
	@NotNull
	@Pattern(regexp = "\\w{3}")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@NotNull
	@Size(max=60)
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@NotNull
	@Pattern(regexp = "\\w{1,6}")
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}

	@NotNull
	@Pattern(regexp = "\\d+(.\\d*){0,1},\\d+(.\\d*){0,1}")
	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	
	@NotNull
	@Size(max=10)
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

	
	@NotNull
	@Size(max=500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCity() {
		return city;
	}
	
	@NotNull
	@Size(max=20)
	public void setCity(String city) {
		this.city = city;
	}
	
	public native JavaScriptObject bridge() /*-{
		
		return {
			
			shopName : this.@com.opportunity.mainsite.shared.ShopInformation::shopName,
			
			shopType : this.@com.opportunity.mainsite.shared.ShopInformation::shopType,
			
			zipCode : this.@com.opportunity.mainsite.shared.ShopInformation::zipCode,
			
			country : this.@com.opportunity.mainsite.shared.ShopInformation::country,
			
			street : this.@com.opportunity.mainsite.shared.ShopInformation::street,
			
			number : this.@com.opportunity.mainsite.shared.ShopInformation::number,
			
			city : this.@com.opportunity.mainsite.shared.ShopInformation::city,
			
			geoLocation : this.@com.opportunity.mainsite.shared.ShopInformation::geoLocation,
			
			description : this.@com.opportunity.mainsite.shared.ShopInformation::description,
			
			email : this.@com.opportunity.mainsite.shared.ShopInformation::email
		};
		
	}-*/;
	

}
