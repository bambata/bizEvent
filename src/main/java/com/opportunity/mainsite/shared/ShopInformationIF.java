package com.opportunity.mainsite.shared;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public interface ShopInformationIF {
	
	@NotNull
	@Pattern(regexp=".{3,50}")
	public String getShopName();
	
	public void setShopName(String shopName);
	
	@NotNull
	@Pattern(regexp="\\d{1,10}")
	public int getZipCode();

	public void setZipCode(int zipCode);
	
	@NotNull
	@Pattern(regexp="\\w{2}")
	public String getCountry();

	public void setCountry(String country);
	
	@NotNull
	@Size()
	public String getStreet();

	public void setStreet(String street);
	
	@NotNull
	@Pattern(regexp="\\d{1,6}")
	public int getNumber();

	public void setNumber(int number);
	
	@NotNull
	@Pattern(regexp="\\d+(.\\d*){0,1},\\d+(.\\d*){0,1}")
	public String getGeoLocation();

	public void setGeoLocation(String geoLocation);
	
	@NotNull
	public String getShopType();

	public void setShopType(String shopType);
	
	public String getSubType();

	public void setSubType(String subType);
	
	@Pattern(regexp=".{0,300}")
	public String getDescription();
	
	public void setDescription(String description);
	
	@Pattern(regexp="\\w{1,30}")
	public String getCity();

	public void setCity(String city);
	
	@Email
	public String getEmail();

	public void setEmail(String email);
	
}
