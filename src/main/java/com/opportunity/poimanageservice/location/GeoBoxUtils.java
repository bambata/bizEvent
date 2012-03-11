package com.opportunity.poimanageservice.location;

import java.math.BigDecimal;

/**
 * 
 * @author Julien Abega GeoBoxUtils is used to compute geobox
 */
public class GeoBoxUtils {

	private static BigDecimal[] computeTuple(String lat, String lon, int resolution,
			int slice) {

		BigDecimal latitude = new BigDecimal(lat);
		BigDecimal longitude = new BigDecimal(lon);

		BigDecimal sliceOffset = new BigDecimal(slice + "e-" + resolution);

		BigDecimal adjustedLat = roundSliceDown(latitude, sliceOffset);
		BigDecimal adjustedLon = roundSliceDown(longitude, sliceOffset);

		return new BigDecimal[]{adjustedLat, adjustedLon.add(sliceOffset.negate()), adjustedLat.add(sliceOffset.negate()), adjustedLon};
		
	}
	
	public static String computeGeoBox(String lat, String lon, int resolution, int slice){
		
		return formatTuple(computeTuple(lat, lon, resolution, slice), resolution);
		
	}
	
	
	private static String formatTuple(BigDecimal[] geoBox, int resolution){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		String stringFormat = "%1$." + resolution + "f";
		
		for(int i=0;i<geoBox.length;i++){
			
			stringBuilder.append(String.format(stringFormat, geoBox[i]));
			if(i != geoBox.length - 1)
				stringBuilder.append("|");
				
		}
		
		return stringBuilder.toString().replace(',', '.');
		
	}
	
	private static BigDecimal roundSliceDown(BigDecimal coord, BigDecimal sliceOffset) {

		BigDecimal remainder = coord.add(sliceOffset.multiply(coord.divideToIntegralValue(sliceOffset)).negate());

		if (coord.abs().compareTo(sliceOffset.abs()) <= 0)
			return coord;

		if (coord.compareTo(BigDecimal.ZERO) <= 0)
			return coord.add(remainder.negate());

		return coord.add(remainder.negate()).add(sliceOffset);
	}

	public String[] computeGeoBoxSet(String lat, String lon, int resolution, int slice) {

		BigDecimal[] primaryBox = computeTuple(lat, lon, resolution, slice);

		BigDecimal sliceOffset = new BigDecimal(slice + "e-" + resolution);

		BigDecimal latOffset = sliceOffset;
		if (new BigDecimal(lat).min(sliceOffset.divide(BigDecimal.valueOf(2.0))).compareTo(primaryBox[2]) <= 0)
			latOffset = sliceOffset.negate();

		BigDecimal lonOffset = sliceOffset;
		if (new BigDecimal(lon).min(sliceOffset.divide(BigDecimal.valueOf(2.0))).compareTo(primaryBox[4]) <= 0)
			lonOffset = lonOffset.negate();
		
		BigDecimal[] lonBox = new BigDecimal[] { primaryBox[0],
				primaryBox[1].add(lonOffset), primaryBox[2],
				primaryBox[3].add(lonOffset) };
		BigDecimal[] latBox = new BigDecimal[] { primaryBox[0].add(latOffset),
				primaryBox[1], primaryBox[2].add(latOffset),
				primaryBox[3] };
		
		BigDecimal[] diagBox = new BigDecimal[] {
				primaryBox[0].add(latOffset),
				primaryBox[1].add(lonOffset), primaryBox[2].add(latOffset),
				primaryBox[3].add(lonOffset)};

		String[] toReturn = new String[] { formatTuple(primaryBox, resolution),
				formatTuple(latBox, resolution),
				formatTuple(lonBox, resolution),
				formatTuple(diagBox, resolution) };

		return toReturn;
	}

}
