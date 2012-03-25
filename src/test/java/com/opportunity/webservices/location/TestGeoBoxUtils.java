package com.opportunity.webservices.location;

import org.junit.Test;

import com.opportunity.webservices.location.GeoBoxUtils;

import static org.junit.Assert.*;

public class TestGeoBoxUtils {
	
	@Test
	public void ComputeGeoBoxForDifferentPrecision(){
		
		assertEquals(GeoBoxUtils.computeGeoBox("37.78452", "-122.39532", 6, 10), "37.784530|-122.395330|37.784520|-122.395320");
		assertEquals(GeoBoxUtils.computeGeoBox("37.78452", "-122.39532", 6, 25), "37.784525|-122.395325|37.784500|-122.395300");
		assertEquals(GeoBoxUtils.computeGeoBox("37.78452", "-122.39532", 7, 25), "37.7845225|-122.3953225|37.7845200|-122.3953200");
		assertEquals(GeoBoxUtils.computeGeoBox("37.78452", "-122.39532", 7, 1), "37.7845201|-122.3953201|37.7845200|-122.3953200");
		assertEquals(GeoBoxUtils.computeGeoBox("37.78452", "-122.39532", 5, 15), "37.78455|-122.39535|37.78440|-122.39520");
		
	}
	
	
}
