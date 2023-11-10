package kr.codesquad.jazzmeet.venue.dto.response;

import java.util.List;

import kr.codesquad.jazzmeet.venue.vo.VenueDetailImage;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailLink;
import kr.codesquad.jazzmeet.venue.vo.VenueDetailVenueHour;
import lombok.Builder;

@Builder
public record VenueDetailResponse(
	long id,
	List<VenueDetailImage> images,
	String name,
	String roadNameAddress,
	String lotNumberAddress,
	String phoneNumber,
	List<VenueDetailLink> links,
	List<VenueDetailVenueHour> venueHours,
	String description,
	double latitude,
	double longitude
) {
}
