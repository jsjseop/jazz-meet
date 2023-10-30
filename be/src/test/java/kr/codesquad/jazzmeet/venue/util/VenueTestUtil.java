package kr.codesquad.jazzmeet.venue.util;

import org.locationtech.jts.geom.Point;

import kr.codesquad.jazzmeet.venue.entity.Venue;

public class VenueTestUtil {
	public static Venue createVenues(String name, String address, Point point) {

		return Venue.builder()
			.name(name)
			.roadNameAddress(address)
			.location(point)
			.thumbnailUrl("thumbnail.url")
			.lotNumberAddress("지번주소")
			.build();
	}
}
