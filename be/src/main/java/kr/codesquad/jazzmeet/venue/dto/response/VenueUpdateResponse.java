package kr.codesquad.jazzmeet.venue.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.venue.vo.VenueDetailImage;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailLink;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailVenueHour;
import lombok.Builder;

@Builder
public record VenueUpdateResponse(
	long id,
	String name,
	List<VenueDetailImage> images,
	String roadNameAddress,
	String lotNumberAddress,
	String phoneNumber,
	String description,
	List<VenueDetailLink> links,
	List<VenueDetailVenueHour> venueHours,
	double latitude,
	double longitude
) {
}
