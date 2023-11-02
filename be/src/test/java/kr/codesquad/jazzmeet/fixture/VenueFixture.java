package kr.codesquad.jazzmeet.fixture;

import org.locationtech.jts.geom.Point;

import kr.codesquad.jazzmeet.image.entity.Image;
import kr.codesquad.jazzmeet.venue.entity.Venue;
import kr.codesquad.jazzmeet.venue.entity.VenueImage;
import kr.codesquad.jazzmeet.venue.util.VenueUtil;

public class VenueFixture {
	public static Venue createVenue(String name, String address, Point point) {

		return Venue.builder()
			.name(name)
			.roadNameAddress(address)
			.location(point)
			.thumbnailUrl("thumbnail.url")
			.lotNumberAddress("지번주소")
			.build();
	}

	public static Venue createVenue(String name, String address) {
		String lotNumberAddress = "지번";
		Point point = VenueUtil.createPoint(123.123, 32.111);

		return Venue.builder()
			.name(name)
			.roadNameAddress(address)
			.lotNumberAddress(lotNumberAddress)
			.location(point)
			.build();
	}

	public static VenueImage createVenueImage(Venue venue, Image image, long imageOrder) {
		return VenueImage.builder()
			.venue(venue)
			.image(image)
			.imageOrder(imageOrder)
			.build();
	}
}
