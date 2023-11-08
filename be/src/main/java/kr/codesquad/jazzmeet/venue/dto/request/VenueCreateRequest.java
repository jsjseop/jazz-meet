package kr.codesquad.jazzmeet.venue.dto.request;

import java.util.List;

public record VenueCreateRequest(
	String name,
	List<Long> imageIds,
	String address,
	String phoneNumber,
	String description,
	List<VenueCreateLink> links,
	List<VenueCreateHour> venueHours,
	double latitude,
	double longitude
) {
}
