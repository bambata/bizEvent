package com.opportunity.webservices.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed
public class ZoneHub {

	@Id
	private Long id;

	private String zoneName;

	@Indexed
	private String geoBoxLargeResolution;

	@Indexed
	private String geoBoxSmallResolution;

	private String XMPPTopicName;

	@Override
	public boolean equals(Object object) {

		if (object == null)
			return false;

		if (!(object instanceof ZoneHub))
			return false;

		ZoneHub zoneHub = (ZoneHub) object;

		if (geoBoxLargeResolution.equals(zoneHub.getGeoBoxLargeResolution())
				&& geoBoxSmallResolution.equals(zoneHub
						.getGeoBoxSmallResolution())
				&& zoneName.equals(zoneHub.getZoneName())
				&& XMPPTopicName.equals(zoneHub.getXMPPTopicName()))
			return true;
		
		return false;
			
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
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

	public String getXMPPTopicName() {
		return XMPPTopicName;
	}

	public void setXMPPTopicName(String xMPPTopicName) {
		XMPPTopicName = xMPPTopicName;
	}

}
