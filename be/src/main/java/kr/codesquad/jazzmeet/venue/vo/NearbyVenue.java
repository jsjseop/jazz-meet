package kr.codesquad.jazzmeet.venue.vo;

import org.locationtech.jts.geom.Point;

public record NearbyVenue(
	Long id,
	String name,
	String address,
	Point location,
	String thumbnailUrl
) {
}
