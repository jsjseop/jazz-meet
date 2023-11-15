package kr.codesquad.jazzmeet.venue.dto.request;

import org.locationtech.jts.geom.Polygon;

import kr.codesquad.jazzmeet.venue.controller.annotation.RangeCoordinates;
import kr.codesquad.jazzmeet.venue.util.LocationUtil;
import lombok.Builder;

@Builder
@RangeCoordinates
public record RangeCoordinatesRequest(
	Double lowLatitude,
	Double highLatitude,
	Double lowLongitude,
	Double highLongitude
) {

	public boolean hasNull() {
		return lowLatitude == null || highLatitude == null || lowLongitude == null || highLongitude == null;
	}

	public Polygon toRange() {
		return LocationUtil.createRange(lowLatitude, highLatitude, lowLongitude, highLongitude);
	}
}
