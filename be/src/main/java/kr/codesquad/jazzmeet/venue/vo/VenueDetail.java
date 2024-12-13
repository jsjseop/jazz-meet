package kr.codesquad.jazzmeet.venue.vo;

import java.util.Set;

import org.locationtech.jts.geom.Point;

import kr.codesquad.jazzmeet.review.vo.ReviewDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VenueDetail {
	private Long id;
	private String name;
	private String roadNameAddress;
	private String lotNumberAddress;
	private String phoneNumber;
	private String description;
	private Point location;
	private Set<VenueDetailImage> images;
	private Set<VenueDetailLink> links;
	private Set<VenueDetailVenueHour> venueHours;
	private Set<ReviewDetail> reviews;

	@Builder
	public VenueDetail(Long id, String name, String roadNameAddress, String lotNumberAddress, String phoneNumber,
		String description, Point location, Set<VenueDetailImage> images, Set<VenueDetailLink> links,
		Set<VenueDetailVenueHour> venueHours, Set<ReviewDetail> reviews) {
		this.id = id;
		this.name = name;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
		this.phoneNumber = phoneNumber;
		this.description = description;
		this.location = location;
		this.images = images;
		this.links = links;
		this.venueHours = venueHours;
		this.reviews = reviews;
	}
}
