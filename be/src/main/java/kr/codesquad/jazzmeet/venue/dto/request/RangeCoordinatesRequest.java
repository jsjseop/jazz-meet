package kr.codesquad.jazzmeet.venue.dto.request;

import org.locationtech.jts.geom.Polygon;

import kr.codesquad.jazzmeet.venue.util.LocationUtil;
import lombok.Builder;

@Builder
public record RangeCoordinatesRequest(
	Double lowLatitude,
	Double highLatitude,
	Double lowLongitude,
	Double highLongitude
) {

	public boolean isValidCoordinates() {
		return lowLatitude != null && highLatitude != null && lowLongitude != null && highLongitude != null;
	}

	public Polygon toRange() {
		return LocationUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);
	}
}
